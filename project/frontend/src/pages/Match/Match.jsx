import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import "./Match.css"
import "./Statistic/TeamBlock/TeamRow/TeamRow.css";
import MatchHeader from "./MatchHeader/MatchHeader";
import MatchMap from "./MatchMap/MatchMap"
import Description from "./Description/Description";
import ScrollLog from "./ScrollLog/ScrollLog"
import Preloader from "../../components/Preloader/Preloader";
import TeamRosters from "./TeamRosters/TeamRosters"
import { request } from "../../utils/Utils";

function Match() {
    const params = useParams();

    const [match, setMatch] = useState(null);


    async function getFullMatch() {
        let match = await request("GET", `match/${params.id}`);
        setMatch(match.data[0]);
    }

    useEffect(() => {
        getFullMatch();
    }, []);


    function getLastMap() {
        let res = match.maps.filter(map => map.logs !== undefined && map.logs[map.logs.length - 1].type !== "ROUND_END" && map.logs[map.logs.length - 1].scoreCT !== 12 && (map.logs[map.logs.length - 1].scoreT !== 12));
        if (res.length == 0) {
            return null;
        }
        return res[0];
    }

    return (
        <div>
            {match !== null ?
                <div style={{ display: "inline-flex", flexDirection: "column", alignItems: "center", gap: "50px", width: "100%" }}>
                    <div>
                        <MatchHeader match={match} matchId={params.id} setMatch={setMatch} />

                        <div className="match_info_upcoming">
                            <div>
                                <Description match={match} setMatch={setMatch} matchId={params.id} />

                                <div className="p_fixer">
                                    {match.maps.map((map, i) =>
                                        <MatchMap logoFirst={match.leftTeam.base.src} nameFirst={match.leftTeam.base.name} logoSecond={match.rightTeam.base.src} nameSecond={match.rightTeam.base.name} map={map} pickedBy={map.pickedBy} key={`${map.map.name}/${i}`} />
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>

                    <div style={{ display: "inline-flex", flexDirection: "column", alignItems: "center", gap: "30px", width: "100%" }}>
                        {match.maps !== undefined && match.maps[0].pickedBy !== "TBA" ?
                            <div className="scroll_logs_block">
                                <p>Игровые события</p>
                                <ScrollLog matchId={params.id} event={match.eventName} logs={getLastMap() === null ? null : getLastMap().logs} mapName={getLastMap() === null ? "TBA" : getLastMap().map.name} />
                            </div>
                            :
                            <></>
                        }

                        <div>
                            <p className="p_fixer" style={{ marginBottom: "5px" }}>Составы команд</p>
                            <div className="col_center_gap10">
                                <TeamRosters {...match.leftTeam} />
                                <TeamRosters {...match.rightTeam} />
                            </div>
                        </div>
                    </div>
                </div>
                :
                <Preloader />
            }


        </div >
    )
}
export default Match;