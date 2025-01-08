import React from "react";
import { getBackgroundImage } from "../../../utils/Utils";
import "./MatchHeader.css"

function MatchHeader(props) {
    return (
        <div>
            <div className="header_match">
                <div className="container_time_match">
                    <div className="container_time_match_time">
                        <a>{props.match.startTime.split("T")[1].substring(0, props.match.startTime.split("T")[1].length - 3)}</a>
                    </div>
                    <div className="container_time_match_date">
                        <a>{props.match.startTime.split("T")[0]}</a>
                    </div>
                    <div className="container_time_match_cup">
                        <p>{props.match.eventName}</p>
                    </div>
                    <div className="container_time_match_live">
                        <a>LIVE</a>
                    </div>
                </div>
                <div className="flag_team" style={{ backgroundSize: "250px 150px", backgroundImage: `linear-gradient(to right, rgba(0, 0, 0, 0.4), rgba(25, 25, 25, 1)), url("${getBackgroundImage(props.match.leftTeam.country.src)}")`, left: "0" }}>
                    <div className="match_header_team">
                        <img src={getBackgroundImage(props.match.leftTeam.base.src)} alt={props.match.leftTeam.base.name} />
                        <p>{props.match.leftTeam.base.name}</p>
                    </div>
                </div>
                <div className="flag_team" style={{ backgroundSize: "250px 150px", backgroundImage: `linear-gradient(to left, rgba(0, 0, 0, 0.4), rgba(25, 25, 25, 1)), url("${getBackgroundImage(props.match.rightTeam.country.src)}")`, right: "0" }}>
                    <div className="match_header_team">
                        <img src={getBackgroundImage(props.match.rightTeam.base.src)} alt={props.match.rightTeam.base.name} />
                        <p>{props.match.rightTeam.base.name}</p>
                    </div>
                </div>
            </div>
        </div >
    )
}

export default MatchHeader;