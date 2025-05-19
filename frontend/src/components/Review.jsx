import { useState, useEffect } from "react";
import "./Review.css";

const Review = ({ id }) => {
  const [reviews, setReviews] = useState([
    {
      writer: "user123",
      content: "정말 재미있었어요!",
      rating: 5,
    },
    {
      writer: "user456",
      content: "배우 연기가 인상 깊었어요.",
      rating: 4,
    },
  ]);
  const [content, setContent] = useState("");
  const [rating, setRating] = useState(10);

  const username = localStorage.getItem("username");

  // ✅ 아이디 마스킹 함수
  const maskUsername = (username) => {
    if (!username) return "";
    if (username.length <= 3) return "*".repeat(username.length);
    return username.slice(0, 3) + "*".repeat(username.length - 3);
  };

  // ✅ 리뷰 목록 불러오기
  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await fetch(
          `${import.meta.env.VITE_BACKEND_API_URL}/movie/${id}/reviews`
        );
        if (!response.ok) throw new Error("리뷰 불러오기 실패");

        const data = await response.json();
        setReviews(data); // [{writer, content, rating, date}]
      } catch (error) {
        console.error("리뷰 불러오기 오류:", error);
      }
    };

    fetchReviews();
  }, [id]);

  // ✅ 리뷰 등록
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!content.trim()) return;

    const newReview = {
      movieId: id,
      writer: username,
      content,
      rating,
      dateTime: new Date().toISOString(),
    };

    try {
      const response = await fetch(
        `${import.meta.env.VITE_BACKEND_API_URL}/movie/${id}`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(newReview),
        }
      );

      if (!response.ok) throw new Error("리뷰 등록 실패");

      // 닉네임 없이 writer만 저장됨
      setReviews([...reviews, newReview]);
      setContent("");
      setRating(10);
    } catch (error) {
      console.error("리뷰 등록 중 오류:", error);
    }
  };

  return (
    <div className="review-container">
      <h2>📝 리뷰</h2>

      <ul className="review-list">
        {reviews.map((review, idx) => (
          <li key={idx} className="review-item">
            <p>
              <strong>{maskUsername(review.writer)}</strong> - 평점:{" "}
              {review.rating}점
            </p>
            <p>{review.content}</p>
          </li>
        ))}
      </ul>

      {/* 테스트용 코드 */}
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

      {/* {username ? (
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
      )} */}
    </div>
  );
};

export default Review;
