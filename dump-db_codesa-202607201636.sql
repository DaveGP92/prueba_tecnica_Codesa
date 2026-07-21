--
-- PostgreSQL database cluster dump
--

-- Started on 2026-07-20 19:30:44

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--
-- CREATE ROLE postgres;
-- ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;

--
-- User Configurations
--








--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2026-07-20 19:30:44

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Completed on 2026-07-20 19:30:44

--
-- PostgreSQL database dump complete
--

--
-- Database "db_codesa" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2026-07-20 19:30:44

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3372 (class 1262 OID 16619)
-- Name: db_codesa; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE db_codesa WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Colombia.1252';


ALTER DATABASE db_codesa OWNER TO postgres;

\connect db_codesa

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16636)
-- Name: genres; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.genres (
    id integer NOT NULL,
    description character varying,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.genres OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16635)
-- Name: genres_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.genres_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.genres_id_seq OWNER TO postgres;

--
-- TOC entry 3373 (class 0 OID 0)
-- Dependencies: 216
-- Name: genres_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.genres_id_seq OWNED BY public.genres.id;


--
-- TOC entry 215 (class 1259 OID 16621)
-- Name: plays; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.plays (
    id integer NOT NULL,
    title character varying,
    description text,
    genre_id integer,
    duration_minutes integer,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.plays OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16620)
-- Name: plays_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.plays_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.plays_id_seq OWNER TO postgres;

--
-- TOC entry 3374 (class 0 OID 0)
-- Dependencies: 214
-- Name: plays_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.plays_id_seq OWNED BY public.plays.id;


--
-- TOC entry 219 (class 1259 OID 16646)
-- Name: schedules; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.schedules (
    id integer NOT NULL,
    play_id integer,
    date_time timestamp without time zone,
    room character varying,
    total_seats character varying,
    available_seats character varying,
    base_price character varying,
    active boolean,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.schedules OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16645)
-- Name: schedules_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.schedules_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.schedules_id_seq OWNER TO postgres;

--
-- TOC entry 3375 (class 0 OID 0)
-- Dependencies: 218
-- Name: schedules_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.schedules_id_seq OWNED BY public.schedules.id;


--
-- TOC entry 223 (class 1259 OID 16678)
-- Name: tickets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tickets (
    id integer NOT NULL,
    schedule_id integer,
    user_id integer,
    quantity integer,
    total_price real,
    active boolean,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    customer_name character varying(150),
    customer_email character varying(150),
    customer_phone character varying(50)
);


ALTER TABLE public.tickets OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16677)
-- Name: tickets_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tickets_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tickets_id_seq OWNER TO postgres;

--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 222
-- Name: tickets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tickets_id_seq OWNED BY public.tickets.id;


--
-- TOC entry 221 (class 1259 OID 16665)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    user_name character varying NOT NULL,
    password character varying,
    email character varying NOT NULL,
    full_name character varying,
    role character varying,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16664)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 220
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 3195 (class 2604 OID 16639)
-- Name: genres id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genres ALTER COLUMN id SET DEFAULT nextval('public.genres_id_seq'::regclass);


--
-- TOC entry 3193 (class 2604 OID 16624)
-- Name: plays id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plays ALTER COLUMN id SET DEFAULT nextval('public.plays_id_seq'::regclass);


--
-- TOC entry 3196 (class 2604 OID 16649)
-- Name: schedules id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.schedules ALTER COLUMN id SET DEFAULT nextval('public.schedules_id_seq'::regclass);


--
-- TOC entry 3198 (class 2604 OID 16681)
-- Name: tickets id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets ALTER COLUMN id SET DEFAULT nextval('public.tickets_id_seq'::regclass);


--
-- TOC entry 3197 (class 2604 OID 16668)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3360 (class 0 OID 16636)
-- Dependencies: 217
-- Data for Name: genres; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.genres (id, description) FROM stdin;
1	Drama
\.


--
-- TOC entry 3358 (class 0 OID 16621)
-- Dependencies: 215
-- Data for Name: plays; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.plays (id, title, description, genre_id, duration_minutes, active, created_at, updated_at) FROM stdin;
4	Titanic 3	editado	1	20	t	2026-07-19 14:54:13.04776	2026-07-20 18:12:20.766964
\.


--
-- TOC entry 3362 (class 0 OID 16646)
-- Dependencies: 219
-- Data for Name: schedules; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.schedules (id, play_id, date_time, room, total_seats, available_seats, base_price, active, created_at, updated_at) FROM stdin;
3	4	2026-07-20 19:07:34.574	1	1	2	3000.00	t	\N	\N
4	4	2026-07-20 00:00:00	2	10	16	3200.00	t	\N	2026-07-20 19:27:40.456407
\.


--
-- TOC entry 3366 (class 0 OID 16678)
-- Dependencies: 223
-- Data for Name: tickets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tickets (id, schedule_id, user_id, quantity, total_price, active, created_at, updated_at, customer_name, customer_email, customer_phone) FROM stdin;
\.


--
-- TOC entry 3364 (class 0 OID 16665)
-- Dependencies: 221
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, user_name, password, email, full_name, role, created_at, updated_at) FROM stdin;
1	admin	$2a$10$uCc/mZv3NUVTSv852vNv/unHstBgNGFaM3zQbqJt7yK8yO.A6m.a6	test_codesa@example.com	Codesa Codesa	ADMIN	2026-07-19 14:00:51.495282	2026-07-19 14:00:51.495282
\.


--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 216
-- Name: genres_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.genres_id_seq', 1, true);


--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 214
-- Name: plays_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.plays_id_seq', 6, true);


--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 218
-- Name: schedules_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.schedules_id_seq', 4, true);


--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 222
-- Name: tickets_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tickets_id_seq', 2, true);


--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 220
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- TOC entry 3202 (class 2606 OID 16643)
-- Name: genres genres_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genres
    ADD CONSTRAINT genres_pk PRIMARY KEY (id);


--
-- TOC entry 3200 (class 2606 OID 16626)
-- Name: plays plays_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plays
    ADD CONSTRAINT plays_pk PRIMARY KEY (id);


--
-- TOC entry 3204 (class 2606 OID 16651)
-- Name: schedules schedules_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.schedules
    ADD CONSTRAINT schedules_pk PRIMARY KEY (id);


--
-- TOC entry 3212 (class 2606 OID 16683)
-- Name: tickets tickets_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_pk PRIMARY KEY (id);


--
-- TOC entry 3206 (class 2606 OID 16670)
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- TOC entry 3208 (class 2606 OID 16674)
-- Name: users users_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_unique UNIQUE (user_name);


--
-- TOC entry 3210 (class 2606 OID 16676)
-- Name: users users_unique_1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_unique_1 UNIQUE (email);


--
-- TOC entry 3213 (class 2606 OID 16652)
-- Name: plays plays_genres_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plays
    ADD CONSTRAINT plays_genres_fk FOREIGN KEY (genre_id) REFERENCES public.genres(id);


--
-- TOC entry 3214 (class 2606 OID 16657)
-- Name: schedules schedules_plays_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.schedules
    ADD CONSTRAINT schedules_plays_fk FOREIGN KEY (play_id) REFERENCES public.plays(id);


-- Completed on 2026-07-20 19:30:44

--
-- PostgreSQL database dump complete
--

-- Completed on 2026-07-20 19:30:44

--
-- PostgreSQL database cluster dump complete
--

