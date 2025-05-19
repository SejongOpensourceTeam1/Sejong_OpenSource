import { useState } from "react";
import "./Review.css";

const Review = () => {
  const [reviews, setReviews] = useState([
    { writer: "홍길동", content: "정말 재미있었어요!" },
    { writer: "김영희", content: "배우 연기가 인상 깊었어요." },
  ]);
  const [content, setContent] = useState("");

  const isLoggedIn = !!localStorage.getItem("token");
  const nickname = localStorage.getItem("nickname");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!content.trim()) return;

    setReviews([...reviews, { writer: nickname || "익명", content }]);
    setContent("");
  };

  return (
    <div className="review-container">
      <h2>📝 리뷰</h2>

      {/* 작성된 리뷰 목록 */}
      <ul className="review-list">
        {reviews.map((review, idx) => (
          <li key={idx} className="review-item">
            <p>
              <strong>{review.writer}</strong>
            </p>
            <p>{review.content}</p>
          </li>
        ))}
      </ul>

      {/* 조건부 리뷰 작성 폼 */}
      {isLoggedIn ? (
        <form onSubmit={handleSubmit} className="review-form">
          <textarea
            placeholder="리뷰를 작성해주세요."
            value={content}
            onChange={(e) => setContent(e.target.value)}
          />
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
