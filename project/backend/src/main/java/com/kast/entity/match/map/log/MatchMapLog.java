package com.kast.entity.match.map.log;

import com.kast.entity.match.map.log.enums.BombPlant;
import com.kast.entity.match.map.log.enums.Guns;
import com.kast.entity.match.map.log.enums.LogSide;
import com.kast.entity.match.map.log.enums.LogType;
import com.kast.entity.match.map.log.enums.RoundWonBy;
import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Serdeable
public class MatchMapLog {
    /**
     * Тип лога:
     * <li><b>login</b> - игрок зашел на сервер</li>
     * <li><b>kill</b> - произошло убийство</li>
     * <li><b>bombDeath</b> - игрок умер от взрыва бомбы</li>
     * <li><b>suicide</b> - игрок совершил суицид</li>
     * <li><b>roundStarted</b> - раунд начался</li>
     * <li><b>roundEnd</b> - раунд закончился</li>
     * <li><b>bombPlanted</b> - была установлена бомба</li>
     * <li><b>bombDefused </b>- бомба обезврежена</li>
     * <li><b>logout</b> - игрок вышел с сервера</li>
     */
    @BsonProperty("type")
    private LogType type;

    /**
     * Ник игрока, который:
     * <li>Зашел на сервер</li>
     * <li>Убил другого игрока</li>
     * <li>Умер от бомбы</li>
     * <li>Совершил суицид</li>
     * <li>Установил бомбу</li>
     * <li>Обезвредил бомбу</li>
     * <li>Вышел с сервера</li>
     */
    @BsonProperty("nick")
    private String nick;

    /**
     * Сторона игрока (CT или T), который:
     * <li>Зашел на сервер</li>
     * <li>Убил другого игрока</li>
     * <li>Умер от бомбы</li>
     * <li>Совершил суицид</li>
     * <li>Установил бомбу</li>
     * <li>Обезвредил бомбу</li>
     * <li>Вышел с сервера</li>
     */
    @BsonProperty("side")
    private LogSide side;

    /**
     * Победитель в раунде: CT или T
     */
    @BsonProperty("winner")
    private LogSide winner;

    /**
     * Счет T после окончания раунда
     */
    @BsonProperty("scoreT")
    private Integer scoreT;

    /**
     * Счет CT после окончания раунда
     */
    @BsonProperty("scoreCT")
    private Integer scoreCT;

    /**
     * Как закончился раунд:
     * <li>Взорвана бомба</li>
     * <li>Враги уничтожены</li>
     * <li>Время истекло</li>
     * <li>Бомба обезврежена</li>
     */
    @BsonProperty("how")
    private RoundWonBy how;

    /**
     * Сколько живых игроков T на момент установки бомбы
     */
    @BsonProperty("tAlive")
    private Integer tAlive;

    /**
     * Сколько живых игроков CT на момент установки бомбы
     */
    @BsonProperty("ctAlive")
    private Integer ctAlive;

    /**
     * На какой точке установлена бомба
     */
    @BsonProperty("plant")
    private BombPlant plant;

    /**
     * Был ли убийца ослеплен
     */
    @BsonProperty("attackerblind")
    private Boolean attackerblind;

    /**
     * Ник игрока, которому засчитан ассист в убийстве
     */
    @BsonProperty("assisted")
    private String assisted;

    /**
     * Сторона игрока, которому засчитан ассист в убийстве: CT или T
     */
    @BsonProperty("assisterSide")
    private LogSide assisterSide;

    /**
     * Ник игрока, которому засчитан флеш ассист
     */
    @BsonProperty("flashAssisted")
    private String flashAssisted;

    /**
     * Сторона игрока, которому засчитан флеш ассист: CT или T
     */
    @BsonProperty("flashAssistedSide")
    private LogSide flashAssistedSide;

    /**
     * Оружие, которым было произведено убийство
     */
    @BsonProperty("gun")
    private Guns gun;

    /**
     * Было ли убийство произведено ноускопом - снайперской винтовкой без использования прицела
     */
    @BsonProperty("noscope")
    private Boolean noscope;

    /**
     * Было ли убийство прострелом
     */
    @BsonProperty("penetrated")
    private Boolean penetrated;

    /**
     * Было ли убийство через дым
     */
    @BsonProperty("throughsmoke")
    private Boolean throughsmoke;

    /**
     * Было ли убийство в голову
     */
    @BsonProperty("throughsmoke")
    private Boolean headshot;

    /**
     * Сторона убитого игрока: CT или T
     */
    @BsonProperty("victimSide")
    private LogSide victimSide;

    /**
     * Ник убитого игрока
     */
    @BsonProperty("victim")
    private String victim;

    @Creator
    @BsonCreator
    public MatchMapLog() {
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setSide(LogSide side) {
        this.side = side;
    }

    public void setWinner(LogSide winner) {
        this.winner = winner;
    }

    public void setHow(RoundWonBy how) {
        this.how = how;
    }

    public void setTAlive(Integer tAlive) {
        this.tAlive = tAlive;
    }

    public void setCtAlive(Integer ctAlive) {
        this.ctAlive = ctAlive;
    }

    public void setPlant(BombPlant plant) {
        this.plant = plant;
    }

    public void setAttackerblind(Boolean attackerblind) {
        this.attackerblind = attackerblind;
    }

    public void setAssisted(String assisted) {
        this.assisted = assisted;
    }

    public void setAssisterSide(LogSide assisterSide) {
        this.assisterSide = assisterSide;
    }

    public void setFlashAssisted(String flashAssisted) {
        this.flashAssisted = flashAssisted;
    }

    public void setFlashAssistedSide(LogSide flashAssistedSide) {
        this.flashAssistedSide = flashAssistedSide;
    }

    public void setGun(Guns gun) {
        this.gun = gun;
    }

    public void setNoscope(Boolean noscope) {
        this.noscope = noscope;
    }

    public void setPenetrated(Boolean penetrated) {
        this.penetrated = penetrated;
    }

    public void setThroughsmoke(Boolean throughsmoke) {
        this.throughsmoke = throughsmoke;
    }

    public void setHeadshot(Boolean headshot) {
        this.headshot = headshot;
    }

    public void setVictimSide(LogSide victimSide) {
        this.victimSide = victimSide;
    }

    public void setVictim(String victim) {
        this.victim = victim;
    }

    public LogType getType() {
        return type;
    }

    public String getNick() {
        return nick;
    }

    public LogSide getSide() {
        return side;
    }

    public LogSide getWinner() {
        return winner;
    }

    public Integer getScoreT() {
        return scoreT;
    }

    public void setScoreT(Integer scoreT) {
        this.scoreT = scoreT;
    }

    public Integer getScoreCT() {
        return scoreCT;
    }

    public void setScoreCT(Integer scoreCT) {
        this.scoreCT = scoreCT;
    }

    public RoundWonBy getHow() {
        return how;
    }

    public Integer getTAlive() {
        return tAlive;
    }

    public Integer getCtAlive() {
        return ctAlive;
    }

    public BombPlant getPlant() {
        return plant;
    }

    public Boolean getAttackerblind() {
        return attackerblind;
    }

    public String getAssisted() {
        return assisted;
    }

    public LogSide getAssisterSide() {
        return assisterSide;
    }

    public String getFlashAssisted() {
        return flashAssisted;
    }

    public LogSide getFlashAssistedSide() {
        return flashAssistedSide;
    }

    public Guns getGun() {
        return gun;
    }

    public Boolean getNoscope() {
        return noscope;
    }

    public Boolean getPenetrated() {
        return penetrated;
    }

    public Boolean getThroughsmoke() {
        return throughsmoke;
    }

    public Boolean getHeadshot() {
        return headshot;
    }

    public LogSide getVictimSide() {
        return victimSide;
    }

    public String getVictim() {
        return victim;
    }

    @Override
    public String toString() {
        return ("%s{type=%s, nick=%s, side=%s, winner=%s, scoreT=%d, scoreCT=%d, how=%s, tAlive=%d, ctAlive=%d, plant=%s, " +
                "attackerblind=%b, assisted=%s, assisterSide=%s, flashAssisted=%s, flashAssistedSide=%s, gun=%s, noscope=%b, " +
                "penetrated=%b, throughsmoke=%b, headshot=%b, victimSide=%s, victim=%s}")
                .formatted(this.getClass().getSimpleName(), type, nick, side, winner, scoreT, scoreCT, how, tAlive, ctAlive,
                        plant, attackerblind, assisted, assisterSide, flashAssisted, flashAssistedSide, gun, noscope, penetrated,
                        throughsmoke, headshot, victimSide, victim);
    }
}