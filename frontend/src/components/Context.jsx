import { useEffect, useState } from "react";
import "./Context.css";
import ScrollableSlider from "./ScrollableSlider";

const API_KEY = import.meta.env.VITE_TMDB_API_KEY;

const Context = ({ searchKeyword }) => {
  const [genres, setGenres] = useState([]);
  const [genreMovies, setGenreMovies] = useState({});
  const [topRated, setTopRated] = useState([]);
  const [searchResults, setSearchResults] = useState([]);

  useEffect(() => {
    fetch(
      `https://api.themoviedb.org/3/movie/top_rated?language=ko&api_key=${API_KEY}`
    )
      .then((res) => res.json())
      .then((data) => {
        setTopRated(data.results.slice(0, 10));
      });

    fetch(
      `https://api.themoviedb.org/3/genre/movie/list?language=ko&api_key=${API_KEY}`
    )
      .then((res) => res.json())
      .then((data) => {
        setGenres(data.genres);
        data.genres.forEach((genre) => {
          fetch(
            `https://api.themoviedb.org/3/discover/movie?with_genres=${genre.id}&language=ko&api_key=${API_KEY}`
          )
            .then((res) => res.json())
            .then((movieData) => {
              const sorted = movieData.results.sort(
                (a, b) => b.vote_average - a.vote_average
              );
              setGenreMovies((prev) => ({
                ...prev,
                [genre.name]: sorted,
              }));
            });
        });
      });
  }, []);

  // 검색어가 변경될 때마다 검색 실행
  useEffect(() => {
    if (!searchKeyword) {
      setSearchResults([]);
      return;
    }

    fetch(
      `https://api.themoviedb.org/3/search/movie?query=${encodeURIComponent(
        searchKeyword
      )}&language=ko&api_key=${API_KEY}`
    )
      .then((res) => res.json())
      .then((data) => {
        setSearchResults(data.results);
      });
  }, [searchKeyword]);

  return (
    <div className="genre-wrapper">
      {searchResults.length > 0 ? (
        <ScrollableSlider
          title={`🔍 "${searchKeyword}" 검색 결과`}
          movies={searchResults.slice(0, 18)}
        />
      ) : (
        <>
          <ScrollableSlider
            title="평점 높은 영화 Top 10"
            movies={topRated}
          />
          {genres.map((genre) => (
            <ScrollableSlider
              key={genre.id}
              title={genre.name}
              movies={(genreMovies[genre.name] || []).slice(0, 18)}
            />
          ))}
        </>
      )}
    </div>
  );
};

export default Context;
