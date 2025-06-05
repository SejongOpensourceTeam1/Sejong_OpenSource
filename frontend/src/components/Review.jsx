import { useState, useEffect } from "react";
import "./Review.css";

const Review = ({ id }) => {
  const [reviews, setReviews] = useState([]);
  const [content, setContent] = useState("");
  const [rating, setRating] = useState(10);

  const token = localStorage.getItem("accessToken");
  const isLoggedIn = !!token;

  let username = "";
  if (token) {
    const payload = JSON.parse(atob(token.split(".")[1]));
    username = payload.sub;
  }

  // 리뷰 불러오기
  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const token = localStorage.getItem("accessToken");

        const response = await fetch(
          `${import.meta.env.VITE_BACKEND_API_URL}/reviews/movie/${id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "ngrok-skip-browser-warning": "true",
            },
          }
        );

        if (!response.ok) throw new Error("리뷰 불러오기 실패");
        const data = await response.json();
        setReviews(data);
      } catch (error) {
        console.error("리뷰 불러오기 오류:", error);
      }
    };

    fetchReviews();
  }, [id]);

  // 리뷰 등록
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!content.trim()) return;

    const newReview = {
      movieId: Number(id),
      writer: username,
      content,
      rating,
      dateTime: new Date().toISOString(),
    };

    try {
      const response = await fetch(
        `${import.meta.env.VITE_BACKEND_API_URL}/reviews`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(newReview),
        }
      );

      if (!response.ok) throw new Error("리뷰 등록 실패");

      // ✅ 서버 응답에서 id 포함된 리뷰를 받아와서 추가
      const savedReview = await response.json();
      setReviews([...reviews, savedReview]);

      setContent("");
      setRating(10);
    } catch (error) {
      console.error("리뷰 등록 중 오류:", error);
    }
  };

  // 리뷰 삭제
  const handleDelete = async (reviewId) => {
    if (!window.confirm("리뷰를 삭제하시겠습니까?")) return;

    try {
      const response = await fetch(
        `${import.meta.env.VITE_BACKEND_API_URL}/reviews/${reviewId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.status === 204 || response.ok) {
        setReviews(reviews.filter((r) => r.id !== reviewId));
      } else {
        throw new Error("리뷰 삭제 실패");
      }
    } catch (error) {
      console.error("리뷰 삭제 중 오류:", error);
    }
  };

  return (
    <div className="review-container">
      <h2>📝 리뷰</h2>

      <ul className="review-list">
        {reviews.map((review, idx) => (
          <li key={idx} className="review-item">
            <p>
              <strong>{review.writer}</strong> - 평점: {review.rating}점
            </p>
            <p>{review.content}</p>
            <p>{review.dateTime.substring(0, 10)}</p>
            {review.writer === username && (
              <button
                className="delete-button"
                onClick={() => handleDelete(review.id)}
              >
                삭제
              </button>
            )}
          </li>
        ))}
      </ul>

      {isLoggedIn ? (
        <form onSubmit={handleSubmit} className="review-form">
          <textarea
            placeholder="리뷰를 작성해주세요."
            value={content}
            onChange={(e) => setContent(e.target.value)}
          />
          <label>
            평점:
            <select
              value={rating}
              onChange={(e) => setRating(parseInt(e.target.value))}
            >
              {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((n) => (
                <option key={n} value={n}>
                  {n}
                </option>
              ))}
            </select>
          </label>
          <button type="submit">등록</button>
        </form>
      ) : (
        <p className="review-login-message">
          리뷰를 작성하려면 <strong>로그인</strong>해주세요.
        </p>
      )}
    </div>
  );
  
};

export default Review;
