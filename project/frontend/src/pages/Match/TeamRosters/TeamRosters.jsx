import React from "react";
import { getBackgroundImage } from "../../../utils/Utils";

function TeamRosters(props) {
    return (
        <div>
            <div className="statistic_team_row" style={{ paddingRight: "5px", width: "638px" }}>
                <div className="statistic_team_row_name">
                    <img src={getBackgroundImage(props.base.src)} style={{ width: "23px", height: "23px", marginTop: "1px" }}></img>
                    <p>{props.base.name}</p>
                </div>
            </div>
            <div className="match_roster_rect">
                {props.players.map((player, i) =>
                    <div style={{ display: "flex", flexDirection: "column", gap: "10px", alignItems: "center" }} key={`${player.base.name}_${i}`}>
                        <img src={getBackgroundImage(player.base.src)} style={{ width: "110px" }}></img>
                        <div style={{ display: "flex", flexDirection: "row", gap: "5px", alignItems: "center" }}>
                            <img src={getBackgroundImage(player.country.src)} style={{ width: "18px", height: "12px" }} alt={player.country.name}></img>
                            <p style={{ margin: "0px", color: "white" }}>{player.base.name}</p>
                        </div>
                    </div>
                )
                }
            </div>
        </div>
    );
}

export default TeamRosters;