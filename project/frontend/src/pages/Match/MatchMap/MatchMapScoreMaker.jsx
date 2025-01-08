import React from "react";
import { getBackgroundImage } from "../../../utils/Utils";

function MatchMapScoreMaker(props) {
    return (
        <div className="team" style={props.styleMain}>
            <div className="container" style={{ alignItems: "center", width: "30px", height: "37px" }}>
                <img className="logo_team" src={getBackgroundImage(props.logo)} />
                {props.isPicked ? <div className="pick"><p>ПИК</p></div> : <></>}
            </div>
            <div className="container" style={props.styleChild}>
                <div className={props.nameClass}>
                    <p>{props.name}</p>
                </div>
                <div className={props.scoreClass}>
                </div>
            </div>
        </div>
    );
}

export default MatchMapScoreMaker;