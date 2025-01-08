import React, { useState, useEffect } from "react";
import "./ScrollLog.css"
import Log from "./Log/Log";
import { request } from "../../../utils/Utils";
// import Stomp from "stompjs";
// import SockJS from "sockjs-client";


function ScrollLog({ matchId, event, logs, mapName }) {
    const [logsData, setLogsData] = useState(null);
    const socket = new WebSocket(`ws://localhost:8080/ws/matches/${matchId}`);

    useEffect(() => {
        if (logs) {
            setLogsData(logs.reverse());
        }
    }, []);

    useEffect(() => {
        socket.onopen = function () {
            console.log('Соединение установлено');
        };

        socket.onmessage = function (event) {
            let data = event.data;
            if (data === "Connected!") {
                return;
            }
            setLogsData(JSON.parse(data).reverse());
        };

        socket.onclose = function (event) {
            console.log('Соединение закрыто');
        };

        socket.onerror = function (error) {
            console.log(`Ошибка: ${error.message}`);
        };
    }, []);


    function colorNick(nick) {
        if (nick === "CT")
            return "var(--ct-color)";
        else
            return "var(--t-color)";
    }


    function weaponImg(weapon) {
        return `/img/scrollLog/weapons/${weapon}.svg`;
    }


    function addInfoKill(info) {
        return `/img/scrollLog/howKilled/${info}.svg`;
    }


    function thingsImg(thing) {
        return `/img/scrollLog/accessories/${thing}.svg`;
    }

    function getHowWin(how) {
        switch (how) {
            case "BOMB_EXPLODED":
                return "Бомба взорвана";
            case "ENEMY_ELIMINATED":
                return "Противник уничтожен";
            case "TIME_IS_UP":
                return "Время истекло";
            case "BOMB_DEFUSED":
                return "Бомба обезврежена";
        }
    }


    return (
        <div className="scroll_logs" style={{ backgroundImage: `url(../../img/scoreboard/${mapName}.png)` }}>
            <div className="logs_container">
                {logsData !== null ?
                    logsData.map((log, i) => {
                        switch (log.type) {
                            case "LOGIN":
                                return (
                                    <Log type={"login"} key={`${log.type}/${i}`}>
                                        <div className="event_text" style={{ color: "white" }}>{log.nick}</div><p>зашёл на сервер</p>
                                    </Log>
                                );
                            case "LOGOUT":
                                return (
                                    <Log type={"logout"} key={`${log.type}/${i}`}>
                                        <div className="event_text" style={{ color: colorNick(log.side) }}>{log.nick}</div><p>вышел с сервера</p>
                                    </Log>
                                );
                            case "ROUND_STARTED":
                                return (
                                    <Log type={"roundBegin"} key={`${log.type}/${i}`}>
                                        <p>Раунд начался</p>
                                    </Log>
                                );
                            case "SUICIDE":
                                return (
                                    <Log type={log.side === "TERRORIST" ? "t_suicide" : "ct_suicide"} key={`${log.type}/${i}`}>
                                        <div className="event_text" style={{ color: colorNick(log.side) }}>{log.nick}</div>
                                        <p>совершил суицид</p>
                                    </Log>
                                );
                            case "BOMB_DEATH":
                                return (
                                    <Log type={"bombDeath"} key={`${log.type}/${i}`}>
                                        <img src={addInfoKill("bombDeath")} alt="bombDeath" />
                                        <div className="event_text" style={{ color: colorNick(log.side) }}>{log.nick}</div>
                                    </Log>
                                );
                            case "ROUND_END":
                                return (
                                    <Log type={log.winner === "T" ? "t_win" : "ct_win"} key={`${log.type}/${i}`}>
                                        <p>Раунд завершен - Победитель:</p>
                                        <div className="event_text" style={{ color: colorNick(log.winner) }}>{log.winner}</div>
                                        <div className="display-row-center">
                                            <p>(</p>
                                            <div className="event_text" style={{ color: "var(--t-color)" }}>{log.scoreT}</div>
                                            <p>&nbsp;-&nbsp;</p>
                                            <div className="event_text" style={{ color: "var(--ct-color)" }}>{log.scoreCT}</div>
                                            <p>)&nbsp;-&nbsp;</p>
                                            <div className="event_text" style={{ color: colorNick(log.winner) }}>{getHowWin(log.how)}</div>
                                        </div>
                                    </Log>
                                );
                            case "BOMB_DEFUSED":
                                return (
                                    <Log type={"defuse"} key={`${log.type}/${i}`}>
                                        <div className="event_text" style={{ color: "var(--ct-color)" }}>{log.nick}</div>
                                        <img src={thingsImg("BombDefused")} alt="DefuseKit" />
                                        <p>разминировал бомбу</p>
                                    </Log>
                                );
                            case "BOMB_PLANTED":
                                return (
                                    <Log type={"bomb_planted"} key={`${log.type}/${i}`}>
                                        <div className="event_text" style={{ color: "var(--t-color)" }}>{log.nick}</div>
                                        <img src={thingsImg("Bomb")} alt="Bomb" />
                                        <p>поставил бомбу на {log.plant}</p>
                                        <div className="display-row-center">
                                            <p>(</p>
                                            <div className="event_text" style={{ color: "var(--t-color)" }}>{log.tAlive}</div>
                                            <p>&nbsp;в&nbsp;</p>
                                            <div className="event_text" style={{ color: "var(--ct-color)" }}>{log.ctAlive}</div>
                                            <p> )</p>
                                        </div>
                                    </Log>
                                );
                            case "KILL":
                                return (
                                    <Log type={"kill"} key={`${log.type}/${i}`}>
                                        {log.attackerblind && <img src={addInfoKill("attackerblind")} alt="attackerblind" />}
                                        <div className="event_text" style={{ color: colorNick(log.side) }}>{log.nick}</div>
                                        {log.assisted !== "" && <p>+</p>}
                                        {log.assisted !== "" && <div className="event_text" style={{ color: colorNick(log.assisterSide) }}>{log.assisted}</div>}
                                        {log.flashAssisted !== "" && <p>+</p>}
                                        {log.flashAssisted !== "" && <img src={addInfoKill("flashassist")} alt="flashassist" />}
                                        {log.flashAssisted !== "" && <div className="event_text" style={{ color: colorNick(log.flashAssistedSide) }}>{log.flashAssisted}</div>}
                                        <img src={weaponImg(log.gun)} alt={log.gun} style={{ height: "14px" }} />
                                        {log.noscope && <img src={addInfoKill("noscope")} alt="noscope" />}
                                        {log.penetrated && <img src={addInfoKill("penetrated")} alt="penetrated" />}
                                        {log.throughsmoke && <img src={addInfoKill("throughsmoke")} alt="throughsmoke" />}
                                        {log.headshot && <img src={addInfoKill("headshot")} alt="headshot" />}
                                        <div className="event_text" style={{ color: colorNick(log.victimSide) }}>{log.victim}</div>
                                    </Log>
                                );
                        }
                    })
                    :
                    <></>
                }
                {logsData !== null ? <div className="log" style={{ padding: "4px", width: "100%", background: "none" }}></div> : <></>}
            </div>
        </div>
    )
}

export default ScrollLog;