import React, { useState, useEffect } from "react";
import "./Matches.css";
import MatchesGenerator from "../../components/MatchGenerator/MatchesGenerator";
import Preloader from "../../components/Preloader/Preloader";

function Matches() {
  const [ongoingMatches, setOngoingMatches] = useState(null);
  const [upcomingMatches, setUpcomingMatches] = useState(null);


  async function getOngoingMatches(ongoingMatches) {
    // setOngoingMatches(await getMatchesWithImg(ongoingMatches));
  }


  async function getUpcomingMatches(upcomingMatches) {
    setUpcomingMatches(
      await Promise.all(upcomingMatches.map(async day => ({
        ...day,
        // matches: await getMatchesWithImg(day.matches)
      })))
    );
  }


  async function getFullMatches() {
    // const matches = await request("GET", `/getFullMatches}`, {}, applHeaders);
    
    getOngoingMatches(matches.data.ongoingMatches);
    getUpcomingMatches(matches.data.upcomingMatches);
  }


  useEffect(() => {
    // getFullMatches();
  }, []);


  return (
    <div>
      {ongoingMatches && upcomingMatches ?
        <MatchesGenerator
          ongoing_matches={ongoingMatches}
          upcoming_matches={upcomingMatches}
        />
        :
        <Preloader />
      }
    </div>
  );
}

export default Matches;