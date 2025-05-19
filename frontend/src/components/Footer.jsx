import { useState } from "react";
import "./Footer.css";
import instaIcon from "../assets/insta.png";
import sejongLogo from "../assets/sejong.png";

const infoContent = {
  "팀 소개": [
    "세종대학교 소프트웨어학과",
    "백엔드: 염지환, 황순영",
    "프론트엔드: 김동은, 이영빈",
  ],
  "이용 안내": "이 사이트는 영화 리뷰를 등록, 열람할 수 있는 커뮤니티입니다.",
  문의하기: "문의: 찾아오세요",
  "개인정보 처리방침":
    "당신의 개인정보는 안전하게 관리됩니다. 외부에 절대 공개되지 않습니다.",
};

const Footer = () => {
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedTitle, setSelectedTitle] = useState("");

  const openModal = (title) => {
    setSelectedTitle(title);
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
    setSelectedTitle("");
  };

  return (
    <div className="footer">
      <div className="footer-top">
        <div className="social-links">
          <a
            href="https://www.instagram.com/sejong_csw/"
            target="_blank"
            rel="noopener noreferrer"
          >
            <img src={instaIcon} alt="Instagram" className="footer-icon" />
          </a>
          <a
            href="http://sejong.ac.kr/index.htm"
            target="_blank"
            rel="noopener noreferrer"
          >
            <img
              src={sejongLogo}
              alt="Sejong University"
              className="sejong-logo"
            />
          </a>
        </div>
      </div>

      <ul className="footer-info">
        {Object.keys(infoContent).map((item) => (
          <li key={item}>
            <span onClick={() => openModal(item)}>{item}</span>
          </li>
        ))}
      </ul>

      <div className="footer-bottom">
        <p>© 2025 CineCampus. All rights reserved.</p>
        <p>세종대학교 소프트웨어학과 | 영화리뷰 커뮤니티 팀 프로젝트</p>
      </div>

      {modalOpen && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-box" onClick={(e) => e.stopPropagation()}>
            <h3>{selectedTitle}</h3>

            {Array.isArray(infoContent[selectedTitle]) ? (
              infoContent[selectedTitle].map((line, idx) => (
                <p key={idx}>{line}</p>
              ))
            ) : (
              <p>{infoContent[selectedTitle]}</p>
            )}

            <button onClick={closeModal}>닫기</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Footer;
