% Fakta gender
laki(anto).
laki(deni).
laki(budi).
laki(rudi).
laki(hadi).
laki(andi).

perempuan(wati).
perempuan(ita).
perempuan(ida).
perempuan(dina).
perempuan(rita).


% Fakta orang tua
% orang tua generasi atas
orangtua(anto, ita).
orangtua(wati, ita).

orangtua(anto, budi).
orangtua(wati, budi).

orangtua(anto, ida).
orangtua(wati, ida).

% keluarga ita
orangtua(ita, hadi).
orangtua(deni, hadi).

% keluarga budi
orangtua(budi, dina).

% keluarga ida
orangtua(ida, andi).
orangtua(rudi, andi).

orangtua(ida, rita).
orangtua(rudi, rita).


% Predikat anak
anak(Anak, Ortu) :-
    orangtua(Ortu, Anak).


% Rule dasar saudara
ortu_pasangan(X, P1, P2) :-
    orangtua(P1, X),
    orangtua(P2, X),
    P1 @< P2.   % kunci urutan term Prolog

% Saudara umum
saudara(X, Y) :-
    ortu_pasangan(X, P1, P2),
    ortu_pasangan(Y, P1, P2),
    X \= Y.

% Saudara Laki-laki
saudara_laki(X, Y) :-
    laki(Y),
    saudara(X, Y).

% Saudara perempuan
saudara_perempuan(X, Y) :-
    perempuan(Y),
    saudara(X, Y).

% Kakek / nenek
kakek(K, C) :-
    laki(K),
    orangtua(K, P),
    orangtua(P, C).
    

nenek(N, C) :-
    perempuan(N),
    orangtua(N, P),
    orangtua(P, C).


% Paman / bibi
paman(P, X) :-
    laki(P),
    saudara(P, O),
    orangtua(O, X).

bibi(B, X) :-
    perempuan(B),
    saudara(B, O),
    orangtua(O, X).