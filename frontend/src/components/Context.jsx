import { useEffect, useState, useRef } from "react";
import "./Context.css";

const API_KEY = import.meta.env.VITE_TMDB_API_KEY;

const ScrollableSlider = ({ title, movies }) => {
  const sliderRef = useRef(null);
  const [isScrolling, setIsScrolling] = useState(false);

  const scrollLeft = () => {
    if (sliderRef.current && !isScrolling) {
      setIsScrolling(true);
      const width = sliderRef.current.clientWidth;
      sliderRef.current.scrollBy({ left: -width, behavior: "smooth" });
      setTimeout(() => setIsScrolling(false), 500);
    }
  };

  const scrollRight = () => {
    if (sliderRef.current && !isScrolling) {
      setIsScrolling(true);
      const width = sliderRef.current.clientWidth;
      sliderRef.current.scrollBy({ left: width, behavior: "smooth" });
      setTimeout(() => setIsScrolling(false), 500);
    }
  };

  return (
    <div className="genre-section">
      <div className="movie-slider-wrapper">
        <div className="genre-label-wrapper">
          <h3 className="genre-label">{title}</h3>
        </div>
        <div className="slider-container">
          <button className="arrow-button circle left" onClick={scrollLeft}>
            &#10094;
          </button>
          <div className="movie-slider" ref={sliderRef}>
            {movies.map((movie) => (
              <div className="movie-card" key={movie.id}>
                {movie.poster_path && (
                  <img
                    src={`https://image.tmdb.org/t/p/w300${movie.poster_path}`}
                    alt={movie.title}
                  />
                )}
                <p className="movie-title">
                  {movie.title}
                  <span className="rating">★ {movie.vote_average?.toFixed(1)}</span>
                </p>
              </div>
            ))}
          </div>
          <button className="arrow-button circle right" onClick={scrollRight}>
            &#10095;
          </button>
        </div>
      </div>
    </div>
  );
};

const Context = () => {
  const [genres, setGenres] = useState([]);
  const [genreMovies, setGenreMovies] = useState({});
  const [topRated, setTopRated] = useState([]);

  useEffect(() => {
    fetch(`https://api.themoviedb.org/3/movie/top_rated?language=ko&api_key=${API_KEY}`)
      .then((res) => res.json())
      .then((data) => {
        setTopRated(data.results.slice(0, 10));
      });

    fetch(`https://api.themoviedb.org/3/genre/movie/list?language=ko&api_key=${API_KEY}`)
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

  return (
    <div className="genre-wrapper">
      <ScrollableSlider title="⭐ 평점 높은 영화 Top 10" movies={topRated} />
      {genres.map((genre) => (
        <ScrollableSlider
          key={genre.id}
          title={genre.name}
          movies={(genreMovies[genre.name] || []).slice(0, 18)}
        />
      ))}
    </div>
  );
};

export default Context;
