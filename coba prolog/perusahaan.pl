% ===== FAKTA =====

bawahan(adi, burhan).

bawahan(burhan, bahrun).
bawahan(burhan, bisrin).

bawahan(bahrun, fahri).
bawahan(bahrun, farah).

bawahan(bisrin, ferdi).


% Relasi atasan
atasan(Atasan, Bawahan) :-
    bawahan(Atasan, Bawahan).

atasan_dari(Bawahan, Atasan) :-
    bawahan(Atasan, Bawahan).


% Baawahan tidak langsung dengan rekursif
bawahan_semua(A, B) :-
    bawahan(A, B).

bawahan_semua(A, C) :-
    bawahan(A, B),
    bawahan_semua(B, C).