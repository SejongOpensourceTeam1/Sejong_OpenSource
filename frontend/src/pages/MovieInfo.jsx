import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";
import Review from "../components/Review";
import "../styles/MovieInfo.css";

const API_KEY = import.meta.env.VITE_TMDB_API_KEY;

const MovieInfo = ({
  isLogin,
  setIsLogin,
  setShowLoginModal,
  setShowRegisterModal,
}) => {
  const { id } = useParams();
  const [movie, setMovie] = useState();
  const [cast, setCast] = useState([]);
  const [trailerKey, setTrailerKey] = useState();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAll = async () => {
      try {
        // 영화 상세 정보
        const res1 = await fetch(
          `https://api.themoviedb.org/3/movie/${id}?api_key=${API_KEY}&language=ko`
        );
        const movieData = await res1.json();
        setMovie(movieData);

        // 배우 정보
        const res2 = await fetch(
          `https://api.themoviedb.org/3/movie/${id}/credits?api_key=${API_KEY}&language=ko`
        );
        const creditsData = await res2.json();
        setCast(creditsData.cast.slice(0, 6)); // 상위 6명만

        // 예고편 정보
        const res3 = await fetch(
          `https://api.themoviedb.org/3/movie/${id}/videos?api_key=${API_KEY}&language=ko`
        );
        const videoData = await res3.json();
        const trailer = videoData.results.find(
          (v) => v.type === "Trailer" && v.site === "YouTube"
        );
        setTrailerKey(trailer?.key || null);
      } catch (err) {
        console.error("데이터 로딩 실패:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchAll();
  }, [id]);

  if (loading) return <div>불러오는 중...</div>;
  if (!movie) return <div>영화를 찾을 수 없습니다.</div>;

  return (
    <div>
      <Header
        isLogin={isLogin}
        setIsLogin={setIsLogin}
        setShowLoginModal={setShowLoginModal}
        setShowRegisterModal={setShowRegisterModal}
      />
      <div className="movie-info-container">
        <h1>{movie.title}</h1>
        <img
          src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
          alt={movie.title}
          className="poster"
        />
        <p>{movie.overview}</p>
        <p>
          <strong>개봉일:</strong> {movie.release_date}
        </p>
        <p>
          <strong>평점:</strong> {movie.vote_average}
        </p>

        <h2>🎭 주요 출연진</h2>
        <div className="cast-list">
          {cast.map((actor) => (
            <div key={actor.id} className="cast-item">
              <img
                src={
                  actor.profile_path
                    ? `https://image.tmdb.org/t/p/w200${actor.profile_path}`
                    : "https://via.placeholder.com/100x150?text=No+Image"
                }
                alt={actor.name}
              />
              <p>
                <strong>{actor.name}</strong>
              </p>
              <p className="character">({actor.character})</p>
            </div>
          ))}
        </div>

        {trailerKey && (
          <>
            <h2>🎬 예고편</h2>
            <div className="trailer">
              <iframe
                width="560"
                height="315"
                src={`https://www.youtube.com/embed/${trailerKey}`}
                title="예고편"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
              ></iframe>
            </div>
          </>
        )}
      </div>
      <Review id={id} />
      <Footer />
    </div>
  );
};

export default MovieInfo;
