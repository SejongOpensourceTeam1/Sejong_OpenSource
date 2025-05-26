import { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./ScrollableSlider.css";

const ScrollableSlider = ({ title, movies }) => {
  const sliderRef = useRef(null);
  const [isScrolling, setIsScrolling] = useState(false);
  const nav = useNavigate();

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
          {/* 화살표는 6개 이상일 때만 보여줌 */}
          {movies.length >= 6 && (
            <button className="arrow-button circle left" onClick={scrollLeft}>
              &#10094;
            </button>
          )}
          <div className="movie-slider" ref={sliderRef}>
            {movies.map((movie) => (
              <div
                className="movie-card"
                key={movie.id}
                onClick={() => nav(`/movie/${movie.id}`)}
              >
                {movie.poster_path && (
                  <img
                    src={`https://image.tmdb.org/t/p/w300${movie.poster_path}`}
                    alt={movie.title}
                  />
                )}
                <p className="movie-title">
                  {movie.title}
                  <span className="rating">
                    ★ {movie.vote_average?.toFixed(1)}
                  </span>
                </p>
              </div>
            ))}
          </div>
          {movies.length >= 6 && (
            <button className="arrow-button circle right" onClick={scrollRight}>
              &#10095;
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default ScrollableSlider;
