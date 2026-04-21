% ===== FAKTA =====
anaklaki(hari, agus).
anaklaki(agus, budi).
anaklaki(ani, rudi).

anakperempuan(agus, ani).
anakperempuan(budi, ria).
anakperempuan(budi, ita).

% ===== RULES =====

% relasi anak umum
anak(X, Y) :- anaklaki(X, Y).
anak(X, Y) :- anakperempuan(X, Y).

% relasi orang tua (benar)
orangtua(Ortu, Anak) :- anak(Ortu, Anak).

% turunan: kasus dasar (langsung) + rekursif
turunan(Ortu, Anak) :-
    orangtua(Ortu, Anak).      % kasus dasar: Ortu adalah orangtua Anak

turunan(Ortu, Cucu) :-
    orangtua(Ortu, Anak),
    turunan(Anak, Cucu).       % rekursif: anak punya turunan -> Ortu punya turunan