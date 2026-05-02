:- module(market_parser, [
    parse_sentence/2,
    parse_text/2
]).

/*
    Parser percakapan di pasar

*/

parse_sentence(Tokens, Tree) :-
    phrase(sentence(Tree), Tokens).

parse_text(Text, Tree) :-
    normalize_space(string(Clean), Text),
    split_string(Clean, " ", ".,?!;:", Parts),
    maplist(string_lower, Parts, Lower),
    maplist(atom_string, Tokens, Lower),
    phrase(sentence(Tree), Tokens).

/* =========================
   LEVEL KALIMAT atau INTENT
   ========================= */

sentence(kalimat(salam(Jenis))) -->
    greeting(Jenis).

sentence(kalimat(tanya_harga(Item))) -->
    price_question(Item).

sentence(kalimat(beli(Qty, Item))) -->
    buy_request(Qty, Item).

sentence(kalimat(tawar)) -->
    bargain.

sentence(kalimat(terima_kasih)) -->
    thanks.

sentence(kalimat(pamitan)) -->
    closing.

/* =========================
   SALAM
   ========================= */

greeting(halo) --> [halo].
greeting(permisi) --> [permisi].
greeting(selamat_pagi) --> [selamat], [pagi].
greeting(selamat_siang) --> [selamat], [siang].
greeting(selamat_sore) --> [selamat], [sore].

/* =========================
   TANYA HARGA
   ========================= */

price_question(Item) -->
    [berapa], [harga], item(Item), [ini].

price_question(Item) -->
    [berapa], [harga], item(Item).

price_question(Item) -->
    [harga], item(Item), [berapa].

price_question(Item) -->
    [berapa], item(Item), [harganya].

/* =========================
   BELI atau PESAN
   ========================= */

buy_request(Qty, Item) -->
    subject,
    [mau], [beli],
    quantity(Qty),
    item(Item).

buy_request(Qty, Item) -->
    subject,
    [beli],
    quantity(Qty),
    item(Item).

buy_request(default_qty, Item) -->
    subject,
    [mau], [beli],
    item(Item).

buy_request(default_qty, Item) -->
    subject,
    [minta],
    item(Item).

subject --> [saya].
subject --> [aku].
subject --> [pak].
subject --> [bu].
subject --> [bapak].
subject --> [ibu].
subject --> [mas].
subject --> [mbak].

quantity(half_kilo) --> [setengah], [kilo].
quantity(one_kilo) --> [satu], [kilo].
quantity(two_kilo) --> [dua], [kilo].
quantity(three_kilo) --> [tiga], [kilo].
quantity(one_kg) --> [sekilo].
quantity(one_pack) --> [satu], [bungkus].
quantity(two_pack) --> [dua], [bungkus].
quantity(one_liter) --> [satu], [liter].
quantity(two_liter) --> [dua], [liter].
quantity(one_ons) --> [satu], [ons].
quantity(two_ons) --> [dua], [ons].

/* =========================
   TAWAR MENAWAR
   ========================= */

bargain --> [bisa], [kurang].
bargain --> [kurang], [sedikit].
bargain --> [boleh], [nego].
bargain --> [mahal], [sekali].
bargain --> [bisa], [turun], [harga].

/* =========================
   UCAPAN PENUTUP
   ========================= */

thanks --> [terima], [kasih].
thanks --> [makasih].

closing --> [sampai], [jumpa].
closing --> [dadah].

/* =========================
   LEXICON PASAR
   ========================= */

item(beras) --> [beras].
item(cabai) --> [cabai].
item(bawang) --> [bawang].
item(tomat) --> [tomat].
item(ayam) --> [ayam].
item(ikan) --> [ikan].
item(telur) --> [telur].
item(gula) --> [gula].
item(minyak) --> [minyak].
item(apel) --> [apel].
item(jeruk) --> [jeruk].
item(sayur) --> [sayur].
item(kentang) --> [kentang].
item(kopi) --> [kopi].
item(krupuk) --> [krupuk].
item(pisang) --> [pisang].
item(teh) --> [teh].
item(susu) --> [susu].
item(daging) --> [daging].
item(ikan_masin) --> [ikan, masin].