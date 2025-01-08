import React from "react";
import "./SubscribeMatch.css"
import ScrollLog from "../../pages/Match/ScrollLog/ScrollLog";

function SubscribeMatch(props) {

    function getLastMap() {
        let res = props.match.maps.filter(map => map.logs !== undefined && map.logs[map.logs.length - 1].type !== "ROUND_END" && map.logs[map.logs.length - 1].scoreCT !== 12 && (map.logs[map.logs.length - 1].scoreT !== 12));
        if (res.length == 0) {
            return null;
        }
        return res[0];
    }

    return (
        <div className="subscribed-match">
            <p>{`${props.match.leftTeam.base.name} vs. ${props.match.rightTeam.base.name}`}</p>
            <ScrollLog matchId={props.match.id} event={props.match.eventName} logs={getLastMap() === null ? null : getLastMap().logs} mapName={getLastMap() === null ? "TBA" : getLastMap().map.name} />
        </div>
    );
}
export default SubscribeMatch;