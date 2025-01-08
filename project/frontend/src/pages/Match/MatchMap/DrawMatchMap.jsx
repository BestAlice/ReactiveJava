import React from "react";
import { getBackgroundImage } from "../../../utils/Utils";

function DrawMatchMap(props) {
    return (
        <div style={{ height: "30px", objectPosition: "center", position: "relative" }}>
            <img src={getBackgroundImage(props.map.src)} alt={props.map.name}style={{width:"330px"}} />
            <div style={{ position: "absolute", top: "50%", left: "50%", transform: "translate(-50%, -50%)" }}>
                <p style={{ margin: "0", fontFamily: "var(--text-regular-lcg)" }}>{props.map.name}</p>
            </div>
        </div>
    );
}

export default DrawMatchMap;