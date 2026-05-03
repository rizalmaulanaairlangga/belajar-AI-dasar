:- module(market_parser, [
    parse_sentence/2,
    parse_text/2
]).

/* 
   Fungsi Utama untuk Parsing 
   --------------------------
   parse_sentence: Menerima list token (kata) dan menghasilkan pohon sintaks (Tree).
   parse_text: Menerima string, melakukan normalisasi, dan memanggil parser.
*/

parse_sentence(Tokens, Tree) :-
    phrase(kalimat(Tree), Tokens).

parse_text(Text, Tree) :-
    normalize_space(string(Clean), Text),
    % Membersihkan spasi dan tanda baca termasuk tanda kutip
    split_string(Clean, " ", ".,?!;:\"'", Parts),
    maplist(string_lower, Parts, Lower),
    maplist(atom_string, Tokens, Lower),
    phrase(kalimat(Tree), Tokens).

/* 
   STRUKTUR GRAMATIKA (SPOK)
   -------------------------
   Mendukung pola: 
   1. S (Subjek)
   2. S-P (Subjek, Predikat)
   3. S-P-O (Subjek, Predikat, Objek)
   4. S-P-O-K (Subjek, Predikat, Objek, Keterangan)
   
   Penyusunan dilakukan dari yang paling kompleks agar Prolog mencoba 
   mencocokkan pola terpanjang terlebih dahulu.
*/

% Pola 4: Subjek + Predikat + Objek + Keterangan (SPOK)
kalimat(pola_spok(S, P, O, K)) --> 
    subjek(S), predikat(P), objek(O), keterangan(K).

% Pola 3: Subjek + Predikat + Objek (SPO)
kalimat(pola_spo(S, P, O)) --> 
    subjek(S), predikat(P), objek(O).

% Pola 2: Subjek + Predikat (SP)
kalimat(pola_sp(S, P)) --> 
    subjek(S), predikat(P).

% Pola 1: Subjek saja (S)
kalimat(pola_s(S)) --> 
    subjek(S).

% Pola Tambahan: Predikat + Objek (PO) - Sering untuk kalimat tanya
% Contoh: "Berapa harga cabai"
kalimat(pola_po(P, O)) --> 
    predikat(P), objek(O).


% =========================
% LEKSIKON (Kamus Kata)
% =========================

% --- SUBJEK (S) ---
% Menampung kata ganti orang atau pelaku di pasar.
subjek(saya) --> [saya].
subjek(saya) --> [aku].
subjek(pembeli) --> [pembeli].
subjek(pedagang) --> [pedagang].
subjek(ibu) --> [ibu].
subjek(bapak) --> [bapak].
subjek(mas) --> [mas].
subjek(mbak) --> [mbak].
% Barang juga bisa menjadi subjek dalam konteks pertanyaan harga
subjek(item(Item)) --> item(Item).

% --- PREDIKAT (P) ---
% Kata kerja atau aksi yang dilakukan di lingkungan pasar.
predikat(ingin) --> [mau].
predikat(membeli) --> [membeli].
predikat(membeli) --> [beli].
predikat(membeli) --> [mau], [beli].
predikat(tanya_harga) --> [berapa], [harga].
predikat(tanya_harga) --> [harga], [berapa].
predikat(tanya_harga) --> [berapa].
predikat(menawar) --> [menawar].
predikat(menawar) --> [nego].
predikat(melihat) --> [melihat].
predikat(melihat) --> [lihat].
predikat(bertanya) --> [bertanya].
predikat(bertanya) --> [tanya].
predikat(menjual) --> [menjual].
predikat(menjual) --> [jual].

% --- OBJEK (O) ---
% Item yang menjadi sasaran aksi. 
% Bisa berupa barang saja atau barang dengan kuantitas.
objek(item(Item)) --> item(Item).
objek(pesanan(Qty, Item)) --> quantity(Qty), item(Item).

% --- KETERANGAN (K) ---
% Memberikan informasi tambahan mengenai tempat, waktu, atau cara.
keterangan(di_pasar) --> [di], [pasar].
keterangan(di_sini) --> [di], [sini].
keterangan(pagi_ini) --> [pagi], [ini].
keterangan(sekarang) --> [sekarang].
keterangan(dengan_murah) --> [dengan], [murah].

/* 
   KOMPONEN TAMBAHAN (Lexicon Item & Quantity)
*/

% Daftar barang yang umum ada di pasar
item(beras) --> [beras].
item(cabai) --> [cabai].
item(ayam) --> [ayam].
item(telur) --> [telur].
item(tomat) --> [tomat].
item(bawang) --> [bawang].
item(ikan) --> [ikan].

% Daftar satuan jumlah/berat
quantity(satu_kilo) --> [satu], [kilo].
quantity(dua_kilo) --> [dua], [kilo].
quantity(sekilo) --> [sekilo].
quantity(setengah_kilo) --> [setengah], [kilo].
quantity(satu_bungkus) --> [satu], [bungkus].