import { useEffect, useState } from "react";
import "./GenreSlider.css";

const API_KEY = import.meta.env.VITE_TMDB_API_KEY;

const GenreSlider = () => {
  const [genres, setGenres] = useState([]);
  const [genreMovies, setGenreMovies] = useState({});

  useEffect(() => {
    fetch(`https://api.themoviedb.org/3/genre/movie/list?language=ko&api_key=${API_KEY}`)
      .then((res) => res.json())
      .then((data) => {
        setGenres(data.genres);

        data.genres.forEach((genre) => {
          fetch(`https://api.themoviedb.org/3/discover/movie?with_genres=${genre.id}&language=ko&api_key=${API_KEY}`)
            .then((res) => res.json())
            .then((movieData) => {
              setGenreMovies((prev) => ({
                ...prev,
                [genre.name]: movieData.results,
              }));
            });
        });
      });
  }, []);

  return (
    <div className="genre-wrapper">
      {genres.map((genre) => (
        <div className="genre-section" key={genre.id}>
          <h3 className="genre-label">{genre.name}</h3>

          <div className="movie-slider">
            {(genreMovies[genre.name] || []).slice(0, 18).map((movie) => (
              <div className="movie-card" key={movie.id}>
                <img
                  src={`https://image.tmdb.org/t/p/w200${movie.poster_path}`}
                  alt={movie.title}
                />
                <p>{movie.title}</p>
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default GenreSlider;
