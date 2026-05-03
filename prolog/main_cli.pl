:- use_module(market_parser).

% Fungsi utama untuk menjalankan program di terminal
start :-
    writeln('========================================'),
    writeln('   NLP Pasar - Prolog Standalone CLI    '),
    writeln('========================================'),
    writeln('Masukkan kalimat (atau ketik "keluar" untuk berhenti):'),
    loop.

loop :-
    write('> '),
    read_line_to_string(user_input, Input),
    (   Input == "keluar" 
    ->  writeln('Sampai jumpa!'), !
    ;   process_input(Input),
        loop
    ).

process_input(Input) :-
    (   parse_text(Input, Tree)
    ->  format('Hasil Parse: ~w~n~n', [Tree])
    ;   writeln('Maaf, kalimat tidak dikenali oleh gramatika.'),
        writeln('Pastikan ejaan benar (misal: "berapa harga cabai").\n')
    ).
