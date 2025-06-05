import { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./ScrollableSlider.css";

const ScrollableSlider = ({ title, movies }) => {
  const sliderRef = useRef(null); // 슬라이더 DOM 참조
  const [isScrolling, setIsScrolling] = useState(false); // 중복 스크롤 방지 상태
  const nav = useNavigate(); // 페이지 이동용 훅

  // 왼쪽으로 슬라이드
  const scrollLeft = () => {
    if (sliderRef.current && !isScrolling) {
      setIsScrolling(true);
      const width = sliderRef.current.clientWidth; // 컨테이너 너비 기준
      sliderRef.current.scrollBy({ left: -width, behavior: "smooth" }); // 왼쪽으로 스크롤
      setTimeout(() => setIsScrolling(false), 500); // 0.5초 후 다시 스크롤 가능
    }
  };

  // 오른쪽으로 슬라이드
  const scrollRight = () => {
    if (sliderRef.current && !isScrolling) {
      setIsScrolling(true);
      const width = sliderRef.current.clientWidth;
      sliderRef.current.scrollBy({ left: width, behavior: "smooth" }); // 오른쪽으로 스크롤
      setTimeout(() => setIsScrolling(false), 500);
    }
  };

  return (
    <div className="genre-section">
      <div className="movie-slider-wrapper">
        {/* 장르 제목 출력 */}
        <div className="genre-label-wrapper">
          <h3 className="genre-label">{title}</h3>
        </div>
        {/* 슬라이더 및 버튼 포함 영역 */}
        <div className="slider-container">
          {/* 좌측 화살표 버튼 (영화 6개 이상일 때만 표시) */}
          {movies.length >= 6 && (
            <button className="arrow-button circle left" onClick={scrollLeft}>
              &#10094; {/* ◀ */}
            </button>
          )}

          {/* 슬라이더 본체 */}
          <div className="movie-slider" ref={sliderRef}>
            {movies.map((movie) => (
              <div
                className="movie-card"
                key={movie.id}
                onClick={() => nav(`/movie/${movie.id}`)} // 클릭 시 상세페이지 이동
              >
                {/* 포스터가 존재할 경우에만 이미지 출력 */}
                {movie.poster_path && (
                  <img
                    src={`https://image.tmdb.org/t/p/w300${movie.poster_path}`}
                    alt={movie.title}
                  />
                )}
                {/* 영화 제목과 평점 */}
                <p className="movie-title">
                  {movie.title}
                  <span className="rating">
                    ★ {movie.vote_average?.toFixed(1)} {/* 평점 소수 첫째자리까지 */}
                  </span>
                </p>
              </div>
            ))}
          </div>

          {/* 우측 화살표 버튼 (영화 6개 이상일 때만 표시) */}
          {movies.length >= 6 && (
            <button className="arrow-button circle right" onClick={scrollRight}>
              &#10095; {/* ▶ */}
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default ScrollableSlider;
