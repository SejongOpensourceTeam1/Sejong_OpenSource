import { useState } from "react";
import "./Review.css";

const Review = () => {
  const [reviews, setReviews] = useState([
    { writer: "홍길동", content: "정말 재미있었어요!" },
    { writer: "김영희", content: "배우 연기가 인상 깊었어요." },
  ]);
  const [content, setContent] = useState("");

  const token = localStorage.getItem("accessToken");

  let username = "";
  let userId;
  if (token) {
    const payload = JSON.parse(atob(token.split(".")[1]));
    userId = payload.userId;
    username = payload.sub;
  }

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
          `${import.meta.env.VITE_BACKEND_API_URL}/reviews/movie/${id}`
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
  const isLoggedIn = !!localStorage.getItem("token");
  const nickname = localStorage.getItem("nickname");

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!content.trim()) return;

    const newReview = {
      movieId: Number(id),
      writer: Number(userId),
      content,
      rating,
      dateTime: new Date().toISOString(),
    };

    console.log(newReview);

    try {
      const response = await fetch(
        `${import.meta.env.VITE_BACKEND_API_URL}/reviews`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
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
