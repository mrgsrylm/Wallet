INSERT INTO public.user (id, first_name, last_name, username, email, "password")
VALUES (1, 'Admin', 'Poy Wallet', 'administrator', 'super.osystem@gmail.com', '$2a$09$.yAFQ6ALYMtNwwNXlPx07et1jCS9lGns2fVNLePxcTu3bN7GnTdai');

SELECT setval('sequence_user', max(id)) FROM public.user;

INSERT INTO public.user_role (user_id, role_id) VALUES(1, 2);