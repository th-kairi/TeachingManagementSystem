INSERT INTO STAFF (STAFFID, PASSWORD, NAME, KFLAG) VALUES
('S001', 'pass1234', '田中 太郎', 'Y'),
('S002', 'pass5678', '鈴木 花子', 'N'),
('S003', 'pass9012', '佐藤 次郎', 'Y'),
('S004', 'pass3456', '山田 三郎', 'N'),
('S005', 'pass7890', '高橋 四郎', 'Y');

INSERT INTO CLASSLIST (CLASSCD, NAME) VALUES
('131', 'システム開発コース'),
('132', 'AIシステム開発コース'),
('231', 'システム開発コース'),
('232', 'AIシステム開発コース'),
('331', 'システム開発コース');

INSERT INTO STAFFTOCLASS (STAFFID, CLASSCD) VALUES
('S001', '131'),
('S001', '132'),
('S003', '231'),
('S004', '232'),
('S005', '331');


