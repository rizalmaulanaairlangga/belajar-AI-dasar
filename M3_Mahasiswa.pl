/* Fakta */

mahasiswa(andi).
jurusan(andi, elektro).

matakuliah(kalkulus).
matakuliah_sulit(kalkulus).

tidak_hadir(andi, kalkulus).


/* Aturan */

% Mahasiswa elektro adalah mahasiswa teknik
% menyatakan siapapun X yang merupakan mahasiswa dan mempunyai jurusan elektro adalah mahasiswa teknik
mahasiswa_teknik(X) :-
    mahasiswa(X),
    jurusan(X, elektro).

% Mahasiswa yang tidak hadir pada matakuliah sulit tidak menyukainya
% jika mata kuliah Y sulit, dan mahasiswa X tidak hadir, berarti mahasiswa X membenci mata kuliah Y
benci_matakuliah(X, Y) :-
    mahasiswa(X),
    matakuliah_sulit(Y),    
    tidak_hadir(X, Y).

% Mahasiswa menyukai matakuliah jika tidak membencinya
% jika mahasiswa tidak membenci matakuliah Y, maka ia menyukai matakuliah Y
suka_matakuliah(X, Y) :-
    mahasiswa(X),
    matakuliah(Y),
    \+ benci_matakuliah(X, Y).

% Mahasiswa teknik bisa suka atau tidak suka kalkulus
% jika mahasiswa X tidak membenci matakuliah kalkulus, maka ia menyukai matakuliah kalkulus
suka_matakuliah(X, kalkulus) :-
    mahasiswa_teknik(X),
    \+ benci_matakuliah(X, kalkulus).


