package com.kast.hltv.entity.match.map.log;

import com.kast.hltv.entity.match.map.log.enums.BombPlant;
import com.kast.hltv.entity.match.map.log.enums.LogSide;
import com.kast.hltv.entity.match.map.log.enums.LogType;
import com.kast.hltv.entity.match.map.log.enums.RoundWonBy;
import lombok.Builder;
import lombok.Data;

/**
 * @author Kirill "Tamada" Simovin
 */
@Data
@Builder
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
    private LogSide side;

    //round end
    /**
     * Победитель в раунде: CT или T
     */
    private LogSide winner;

    /**
     * Счет T после окончания раунда
     */
    private Integer scoreT;

    /**
     * Счет CT после окончания раунда
     */
    private Integer scoreCT;

    /**
     * Как закончился раунд:
     * <li>Взорвана бомба</li>
     * <li>Враги уничтожены</li>
     * <li>Время истекло</li>
     * <li>Бомба обезврежена</li>
     */
    private RoundWonBy how;

    // bomb planted
    /**
     * Сколько живых игроков T на момент установки бомбы
     */
    private Integer tAlive;

    /**
     * Сколько живых игроков CT на момент установки бомбы
     */
    private Integer ctAlive;

    /**
     * На какой точке установлена бомба
     */
    private BombPlant plant;

    //kill
    /**
     * Был ли убийца ослеплен
     */
    private Boolean attackerblind;

    /**
     * Ник игрока, которому засчитан ассист в убийстве
     */
    private String assisted;

    /**
     * Сторона игрока, которому засчитан ассист в убийстве: CT или T
     */
    private LogSide assisterSide;

    /**
     * Ник игрока, которому засчитан флеш ассист
     */
    private String flashAssisted;

    /**
     * Сторона игрока, которому засчитан флеш ассист: CT или T
     */
    private LogSide flashAssistedSide;

    /**
     * Оружие, которым было произведено убийство
     */
    private String gun;

    /**
     * Было ли убийство произведено ноускопом - снайперской винтовкой без использования прицела
     */
    private Boolean noscope;

    /**
     * Было ли убийство прострелом
     */
    private Boolean penetrated;

    /**
     * Было ли убийство через дым
     */
    private Boolean throughsmoke;

    /**
     * Было ли убийство в голову
     */
    private Boolean headshot;

    /**
     * Сторона убитого игрока: CT или T
     */
    private LogSide victimSide;

    /**
     * Ник убитого игрока
     */
    private String victim;
}