INSERT INTO STAFF (STAFFID, PASSWORD, NAME, KFLAG) VALUES
('S001', 'pass1234', '�c�� ���Y', 'Y'),
('S002', 'pass5678', '��� �Ԏq', 'N'),
('S003', 'pass9012', '���� ���Y', 'Y'),
('S004', 'pass3456', '�R�c �O�Y', 'N'),
('S005', 'pass7890', '���� �l�Y', 'Y');

INSERT INTO CLASSLIST (CLASSCD, NAME) VALUES
('131', '�V�X�e���J���R�[�X'),
('132', 'AI�V�X�e���J���R�[�X'),
('231', '�V�X�e���J���R�[�X'),
('232', 'AI�V�X�e���J���R�[�X'),
('331', '�V�X�e���J���R�[�X');

INSERT INTO STAFFTOCLASS (STAFFID, CLASSCD) VALUES
('S001', '131'),
('S001', '132'),
('S003', '231'),
('S004', '232'),
('S005', '331');


