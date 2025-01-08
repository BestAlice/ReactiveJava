import React, { useState } from "react";
import "../../pages/Matches/Matches.css";
import "../Tabs/Match/Match.css"
import "../ResultMaker/ResultMaker.css"
import { Link } from "react-router-dom";
import { getBackgroundImage, request } from "../../utils/Utils";
import GradeIcon from '@mui/icons-material/Grade';

function OngoingMatchMaker(props) {
    const [isSubscribed, setIsSubscribed] = useState(props.subscribed);

    async function updateSubscribe() {
        await request("POST", `match/subscribe/${props.id}`);
        setIsSubscribed(!isSubscribed);
    }

    return (
        <div className="match_frame">
            <Link to={`/match/${props.id}`} style={{ textDecoration: "none" }}>
                <div className="status_match_wrapper">
                    <div className="live"><p>LIVE</p></div>
                    <div className="matches_frame">
                        <div className="row_center_gap3">
                            <div className="left_team_tag"><p>{props.leftTeam.base.name}</p></div>
                            <div className="match_frame_img_wrapper">
                                <img src={getBackgroundImage(props.leftTeam.base.src)} alt={props.leftTeam.base.name} key={props.leftTeam.base.name} />
                            </div>
                        </div>
                        <div className="match_score" style={{ flexDirection: props.maps.length > 1 ? "column" : "row" }}>
                            <p style={{ margin: "0px" }}>VS</p>
                        </div>
                        <div className="row_center_gap3">
                            <div className="match_frame_img_wrapper">
                                <img src={getBackgroundImage(props.rightTeam.base.src)} alt={props.rightTeam.base.name} key={props.rightTeam.base.name} />
                            </div>
                            {/* <div className="right_team_tag"><p>{fixTagLength(props.rightTag)}</p></div> */}
                            <div className="right_team_tag"><p>{props.rightTeam.base.name}</p></div>
                        </div>
                    </div>
                </div>
            </Link>
            <div className="row_center_gap3">
                <div className="event">
                    <p>{props.eventName}</p>
                </div>
            </div>
            <div className="match_tier">
                {props.maps.length === 1 ? <p>{props.maps[0].mapName}</p> : <p>{`bo${props.maps.length}`}</p>}
                <GradeIcon className={`subscribe ${isSubscribed ? "subscribed" : ""}`} onClick={() => { updateSubscribe() }} sx={{ fontSize: 20 }} />
            </div>
        </div>
    );
}

export default OngoingMatchMaker;