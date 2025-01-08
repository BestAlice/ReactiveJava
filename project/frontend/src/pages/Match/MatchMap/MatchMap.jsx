import React from "react";
import DrawMatchMap from "./DrawMatchMap";
import MatchMapScoreMaker from "./MatchMapScoreMaker";
import "./MatchMap.css"

function MatchMap({ logoFirst, nameFirst, logoSecond, nameSecond, map, pickedBy }) {

  return (
    <div className={map.map.name === "TBA" ? "map_upcoming" : "map"} style={{ opacity: pickedBy === "DECIDER" ? 0.3 : null }}>

      <DrawMatchMap map={map.map} />

      {
        map.map.name === "TBA" ? null :
          <div className="map_points">
            <MatchMapScoreMaker
              styleMain={null}
              logo={logoFirst}
              styleChild={{ alignItems: "flex-start" }}
              nameClass={"name_first_team"}
              name={nameFirst}
              scoreClass={"score_first_team"}
              isPicked={pickedBy === "LEFT"}
            />

            <MatchMapScoreMaker
              styleMain={{ flexDirection: "row-reverse" }}
              logo={logoSecond}
              styleChild={{ alignItems: "flex-end" }}
              nameClass={"name_second_team"}
              name={nameSecond}
              scoreClass={"score_second_team"}
              isPicked={pickedBy === "RIGHT"}
            />
          </div>
      }
    </div >
  )
}

export default MatchMap;