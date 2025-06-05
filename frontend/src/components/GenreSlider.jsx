import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./GenreSlider.css";

const API_KEY = import.meta.env.VITE_TMDB_API_KEY; // TMDB API 키 환경변수

const GenreSlider = () => {
  const [genres, setGenres] = useState([]); // 장르 목록 상태
  const [genreMovies, setGenreMovies] = useState({}); // 장르별 영화 데이터를 저장할 객체
  const nav = useNavigate(); // 페이지 이동을 위한 useNavigate 훅

  useEffect(() => {
    // 1. 장르 목록 불러오기
    fetch(
      `https://api.themoviedb.org/3/genre/movie/list?language=ko&api_key=${API_KEY}`
    )
      .then((res) => res.json())
      .then((data) => {
        setGenres(data.genres); // 장르 목록 상태 설정

        // 2. 각 장르별 영화 목록 요청
        data.genres.forEach((genre) => {
          fetch(
            `https://api.themoviedb.org/3/discover/movie?with_genres=${genre.id}&api_key=${API_KEY}`
          )
            .then((res) => res.json())
            .then((movieData) => {
              // 기존 상태에 새로운 장르 영화 추가
              setGenreMovies((prev) => ({
                ...prev,
                [genre.name]: movieData.results,
              }));
            });
        });
      });
  }, []); // 컴포넌트 마운트 시 한 번 실행

  return (
    <div className="genre-wrapper">
      {genres.map((genre) => (
        <div className="genre-section" key={genre.id}>
          <h3>{genre.name}</h3> {/* 장르명 출력 */}
          <div className="movie-slider">
            {/* 장르별 영화 카드 출력 (최대 18개) */}
            {(genreMovies[genre.name] || []).slice(0, 18).map((movie) => (
              <div
                className="movie-card"
                key={movie.id}
                onClick={() => nav(`/movie/${movie.id}`)} // 클릭 시 영화 상세 페이지로 이동
              >
                <img
                  src={`https://image.tmdb.org/t/p/w200${movie.poster_path}`} // 영화 포스터 이미지
                  alt={movie.title}
                />
                <p>{movie.title}</p> {/* 영화 제목 */}
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default GenreSlider;
