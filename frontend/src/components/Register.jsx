import "./Modal.css";
import { useState } from "react";

// Register 컴포넌트: 회원가입 모달 UI 및 처리 로직
const Register = ({ closeModal, switchToLogin }) => {
  // 입력 폼 상태
  const [formData, setFormData] = useState({
    nickname: "",
    username: "",
    password: "",
    confirmPassword: "",
  });

  // 입력값 변경 시 상태 업데이트
  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value, // name 속성에 따라 각각 갱신
    }));
  };

  // 회원가입 요청 처리
  const handleSubmit = async (e) => {
    e.preventDefault(); // 폼 기본 동작 방지

    // 비밀번호 확인 검증
    if (formData.password !== formData.confirmPassword) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    try {
      // 서버에 회원가입 POST 요청
      const res = await fetch(
        `${import.meta.env.VITE_BACKEND_API_URL}/api/register`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            nickname: formData.nickname,
            username: formData.username,
            password: formData.password,
          }),
        }
      );

      if (res.ok) {
        alert("회원가입 성공!");
        switchToLogin(); // 로그인 모달로 전환
      } else {
        alert("회원가입 실패");
      }
    } catch (err) {
      console.error(err);
      alert("오류 발생");
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        {/* 닫기 버튼 (x) */}
        <button className="close-button" onClick={closeModal}>
          ×
        </button>

        <h2>회원가입</h2>

        {/* 회원가입 입력 폼 */}
        <form onSubmit={handleSubmit} className="modal-form">
          <input
            type="text"
            name="nickname"
            placeholder="닉네임"
            value={formData.nickname}
            onChange={handleChange}
            required
          />
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
          <input
            type="password"
            name="confirmPassword"
            placeholder="비밀번호 확인"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
          />
          <button type="submit" className="modal-submit">
            가입하기
          </button>
        </form>

        {/* 로그인으로 이동 링크 */}
        <p className="redirect-text">
          이미 계정이 있으신가요?{" "}
          <span className="link-text" onClick={switchToLogin}>
            로그인
          </span>
        </p>
      </div>
    </div>
  );
};

export default Register;
