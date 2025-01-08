package com.kast.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kast.entity.match.Match;
import com.kast.entity.match.map.MatchMap;
import com.kast.entity.match.map.log.MatchMapLog;
import com.kast.entity.match.map.log.enums.LogSide;
import com.kast.entity.match.map.log.enums.LogType;
import com.kast.entity.match.team.Team;
import com.kast.gson.GsonLocalDateTimeAdapter;
import com.kast.interfaces.pause.IPaused;
import com.kast.repository.match.MatchRepositoryImpl;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.scheduling.annotation.Scheduled;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Singleton;
import org.instancio.Instancio;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.instancio.Select.field;

/**
 * @author Kirill "Tamada" Simovin
 */
@Singleton
public class LogsGenerator implements IPaused {
    private static final Logger LOG = LoggerFactory.getLogger(LogsGenerator.class);
    private boolean paused = true;

    private final MatchRepositoryImpl matchRepository;
    private final WebSocketBroadcaster webSocketBroadcaster;

    public LogsGenerator(MatchRepositoryImpl matchRepository, WebSocketBroadcaster webSocketBroadcaster) {
        this.matchRepository = matchRepository;
        this.webSocketBroadcaster = webSocketBroadcaster;
    }

    @Scheduled(fixedDelay = "10s", condition = "#{!this.paused}")
    public void generateMatchesLogs() {
        LOG.info("Logs generator started!");
        List<Match> liveMatches = matchRepository.findByStartTimeBefore(LocalDateTime.now()).collectList().block();
        if (liveMatches != null) {
            liveMatches = liveMatches.stream().filter(m -> m.getStartTime().toLocalDate().isEqual(LocalDate.now())).toList();
        } else {
            LOG.warn("List of matches is null, returning!");
            return;
        }
        
        Observable.fromIterable(liveMatches)
                .flatMap(match -> Observable
                        .just(match)
                        .subscribeOn(Schedulers.io())
                        .doOnNext(this::generateLogs)
                )
                .subscribe();

        LOG.info("Logs generator finished!");
    }

    private void generateLogs(@NotNull Match match) {
        ArrayList<MatchMap> maps = match.getMaps();
        ArrayList<String> allPlayers = new ArrayList<>(getTeamPlayers(match.getLeftTeam()));
        allPlayers.addAll(getTeamPlayers(match.getRightTeam()));

        for (MatchMap map : maps) {
            ArrayList<MatchMapLog> logs = map.getLogs();
            if (!logs.isEmpty() && (logs.getLast().getScoreCT() == 12 || logs.getLast().getScoreT() == 12)) {
                continue;
            }

            MatchMapLog log = Instancio.of(MatchMapLog.class)
                    .generate(field(MatchMapLog::getCtAlive), gen -> gen.oneOf(1, 2, 3, 4, 5))
                    .generate(field(MatchMapLog::getTAlive), gen -> gen.oneOf(1, 2, 3, 4, 5))
                    .generate(field(MatchMapLog::getNick), gen -> gen.oneOf(allPlayers))
                    .generate(field(MatchMapLog::getAssisted), gen -> gen.oneOf(allPlayers))
                    .generate(field(MatchMapLog::getFlashAssisted), gen -> gen.oneOf(allPlayers))
                    .generate(field(MatchMapLog::getVictim), gen -> gen.oneOf(allPlayers))
                    .create();
            setScore(logs, log);
            logs.add(log);

            webSocketBroadcaster.broadcastSync(logs, s -> {
                Map<String, Object> uriVariables = s.getUriVariables().asMap();
                if (uriVariables.isEmpty()){
                    return false;
                }

                Object id = uriVariables.get("id");
                if (id == null){
                    return false;
                }

                return id.toString().equals(String.valueOf(match.getId()));
            });
            break;
        }
        Match res = matchRepository.updateMaps(match.getId(), match).blockingGet();
        LOG.info("Successfully added log for match with id: {}", res.getId());
    }

    private static void setScore(@NotNull ArrayList<MatchMapLog> logs, @NotNull MatchMapLog log) {
        LogType logType = log.getType();

        Integer scoreCT = 0;
        Integer scoreT = 0;
        for (int i = logs.size() - 1; i > -1; --i) {
            if (logs.get(i).getType() != LogType.ROUND_END) {
                continue;
            }

            scoreCT = logs.get(i).getScoreCT();
            scoreT = logs.get(i).getScoreT();
        }

        log.setScoreCT(scoreCT);
        log.setScoreT(scoreT);
        if (logType != LogType.ROUND_END) {
            return;
        }

        if (log.getWinner() == LogSide.CT) {
            log.setScoreCT(scoreCT + 1);
        } else {
            log.setScoreT(scoreT + 1);
        }
    }

    private List<String> getTeamPlayers(@NotNull Team team) {
        return team.getPlayers().stream().map(p -> p.getBase().getName()).toList();
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void onStartup(StartupEvent event) {
        this.paused = false;
        LOG.info("Logs generator enabled!");
    }
}
