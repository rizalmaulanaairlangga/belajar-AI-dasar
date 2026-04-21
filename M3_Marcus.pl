/* ---------------------------------------------------------
   FAKTA
   --------------------------------------------------------- */

% Marcus adalah seorang manusia
man(marcus).

% Marcus adalah penduduk Pompeii
pompeian(marcus).

% Marcus lahir pada tahun 40 M
birth(marcus,40).

% Tahun sekarang adalah 2002
year(2002).


/* ---------------------------------------------------------
   ATURAN 1
   ---------------------------------------------------------
   Semua manusia adalah makhluk fana (mortal).
   Jika X adalah manusia maka X adalah mortal.
   --------------------------------------------------------- */

mortal(X) :-
    man(X).


/* ---------------------------------------------------------
   ATURAN 2
   ---------------------------------------------------------
   Menghitung umur seseorang.
   Umur = tahun sekarang - tahun lahir
   --------------------------------------------------------- */

age(X,AGE) :-
    birth(X,BIRTH),
    year(Y),
    AGE is Y - BIRTH.


/* ---------------------------------------------------------
   ATURAN 3
   ---------------------------------------------------------
   Tidak ada makhluk fana yang hidup lebih dari 150 tahun.
   Jika seseorang mortal dan umurnya lebih dari 150 tahun,
   maka orang tersebut sudah mati.
   --------------------------------------------------------- */

dead(X) :-
    mortal(X),
    age(X,AGE),
    AGE > 150.


/* ---------------------------------------------------------
   ATURAN 4
   ---------------------------------------------------------
   Semua penduduk Pompeii meninggal saat letusan gunung
   Vesuvius pada tahun 79 M.
   Jika seseorang adalah Pompeian dan tahun sekarang
   lebih besar dari 79, maka orang tersebut sudah mati.
   --------------------------------------------------------- */

dead(X) :-
    pompeian(X),
    year(Y),
    Y > 79.