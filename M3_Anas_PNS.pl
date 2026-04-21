/* ---------------------------------------------------------
   FAKTA
   --------------------------------------------------------- */
% Menyatakan bahwa Anas adalah lulusan SD
lulusan_sd(anas).

% Menyatakan bahwa Anas adalah Warga Negara Indonesia
wni(anas).

% Menyatakan bahwa Anas lahir pada tahun 1952
lahir(anas,1952).

% Menyatakan bahwa Anas mencoba mendaftar menjadi PNS
% pada tahun 1985
daftar_pns(anas,1985).

% Menyatakan bahwa tahun sekarang adalah tahun 2005
tahun_sekarang(2005).


/* ---------------------------------------------------------
   MENGHITUNG UMUR
   Umur = Tahun - TahunLahir
   --------------------------------------------------------- */
umur(X,Tahun,Umur) :-
    lahir(X,TLahir),       % mengambil tahun lahir X
    Umur is Tahun - TLahir. % menghitung umur


/* ---------------------------------------------------------
   ATURAN TIDAK BISA MENJADI PNS
   --------------------------------------------------------- */
tidak_bisa_pns(X) :-
    wni(X),                        % X harus WNI
    lulusan_sd(X),                 % X lulusan SD
    daftar_pns(X,TahunDaftar),     % mengambil tahun pendaftaran
    umur(X,TahunDaftar,Umur),      % menghitung umur saat daftar
    Umur > 35.                     % tidak boleh berumur > 35


/* ---------------------------------------------------------
   ATURAN BISA MENJADI PNS
   --------------------------------------------------------- */
bisa_pns(X) :-
    wni(X),                        % X harus WNI
    lulusan_sd(X),                 % X lulusan SD
    daftar_pns(X,TahunDaftar),     % mengambil tahun daftar
    umur(X,TahunDaftar,Umur),      % menghitung umur saat daftar
    Umur =< 35.                    % umur maksimal 35 tahun


/* ---------------------------------------------------------
   ATURAN PENSIUN
   --------------------------------------------------------- */
pensiun(X) :-
    tahun_sekarang(Tahun),     % mengambil tahun sekarang
    umur(X,Tahun,Umur),        % menghitung umur sekarang
    Umur >= 60.                % pensiun umur lebih dari 60 tahun