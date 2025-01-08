import React from "react";
import "../../pages/Matches/Matches.css";
import "../Tabs/Match/Match.css"
import "../ResultMaker/ResultMaker.css"
import { Link } from "react-router-dom";
import { getBackgroundImage } from "../../utils/Utils";

function UpcomingMatchMaker(props) {
    return (
        <div className="match_frame">
            <Link to={`/match/${props.id}`} style={{ textDecoration: "none" }} key={props.id}>
                <div className="status_match_wrapper">
                    <div className="ongoing"><p>{props.startTime.split("T")[1].substring(0, props.startTime.split("T")[1].length - 3)}</p></div>
                    <div className="matches_frame">
                        <div className="match_team">
                            {/* <div className="left_team_tag"><p>{fixTagLength(props.leftTag)}</p></div> */}
                            <div className="left_team_tag"><p>{props.leftTeam.base.name}</p></div>
                            <div className="match_frame_img_wrapper">
                                <img src={getBackgroundImage(props.leftTeam.base.src)} alt={props.leftTeam.base.name} key={props.leftTeam.base.name} />
                            </div>
                        </div>
                        <div className="match_score">
                        </div>
                        <div className="match_team">
                            <div className="match_frame_img_wrapper">
                                <img src={getBackgroundImage(props.rightTeam.base.src)} alt={props.rightTeam.base.name} key={props.rightTeam.base.name} />
                            </div>
                            <div className="right_team_tag"><p>{props.rightTeam.base.name}</p></div>
                        </div>
                    </div>
                </div>
            </Link>
            <div className="row_center_gap3">
                <div className="event">
                    <p>{props.event}</p>
                </div>
            </div>
            <div className="match_tier">
                <div className="row_center_gap3">
                </div>
                {props.maps.length === 1 ? <p>{props.maps[0].mapName}</p> : <p>{`bo${props.maps.length}`}</p>}
            </div>
        </div>
    );
}

export default UpcomingMatchMaker;