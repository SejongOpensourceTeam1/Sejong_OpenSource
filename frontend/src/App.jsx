import "./App.css";
import { Routes, Route } from "react-router-dom";
import { createContext, useEffect, useState } from "react";
import Home from "./Pages/Home";
import Mypage from "./pages/Mypage";

export const MovieContext = createContext();

function App() {
  const API_KEY = import.meta.env.VITE_TMDB_API_KEY;

  const [movies, setMovies] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const pages = [1, 2];
        let allMovies = [];

        for (const page of pages) {
          const res = await fetch(
            `https://api.themoviedb.org/3/movie/popular?api_key=${API_KEY}&language=ko-KR&page=${page}`
          );
          const data = await res.json();
          allMovies = [...allMovies, ...data.results];
        }

        setMovies(allMovies); // 총 40개
      } catch (error) {
        console.error("API 호출 오류:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchMovies();
  }, [API_KEY]);

  if (isLoading) {
    return <div>영화 불러오는 중...</div>;
  }

  return (
    <>
      <MovieContext.Provider value={movies}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/user/mypage" element={<Mypage />} />
        </Routes>
      </MovieContext.Provider>
    </>
  );
}

export default App;
