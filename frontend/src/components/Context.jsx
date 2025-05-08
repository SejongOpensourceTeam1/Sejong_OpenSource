import "./Context.css";
import { MovieContext } from "../App";
import { useContext } from "react";

const Context = () => {
  const movies = useContext(MovieContext);
  return (
    <div>
      <h2>Context 컴포넌트에서 영화 리스트</h2>
      <ul>
        {movies.map((movie) => (
          <li key={movie.id}>
            <img
              src={`https://image.tmdb.org/t/p/w200${movie.poster_path}`}
              alt={movie.title}
            />
            <p>{movie.title}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Context;
