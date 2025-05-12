import "./Modal.css";
import { useState } from "react";

const Login = ({ closeModal, onLoginSuccess, switchToRegister }) => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch(
        `${import.meta.env.VITE_BACKEND_API_URL}/api/login`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(formData),
        }
      );

      if (res.ok) {
        const result = await res.json();
        localStorage.setItem("token", result.token); // ✅ JWT 저장
        localStorage.setItem("nickname", result.nickname); // 선택
        onLoginSuccess();
        closeModal();
      } else {
        alert("로그인 실패: 아이디나 비밀번호를 확인하세요.");
      }
    } catch (err) {
      console.error(err);
      alert("서버 연결 오류");
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <button className="close-button" onClick={closeModal}>
          ×
        </button>
        <h2>로그인</h2>
        <form onSubmit={handleSubmit} className="modal-form">
          <input
            type="text"
            name="username"
            placeholder="아이디"
            value={formData.username}
            onChange={handleChange}
            required
          />
          <input
            type="password"
            name="password"
            placeholder="비밀번호"
            value={formData.password}
            onChange={handleChange}
            required
          />
          <button type="submit" className="modal-submit">
            로그인
          </button>
        </form>
        <p className="redirect-text">
          계정이 없으신가요?{" "}
          <span className="link-text" onClick={switchToRegister}>
            회원가입
          </span>
        </p>
      </div>
    </div>
  );
};

export default Login;
