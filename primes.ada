procedure primes is

    num, j, count : INTEGER;

begin
writeln();
num := 1;
while num <= 100 loop
    if num = 1 then
        write(0);
    else
        j := 1;
        count := 0;
        while j <= num loop
            if num mod j /= 0 then
                count := count + 1;
            endif;
            j := j + 1;
        end loop;
        if count = num - 2 then
            write(",1");
        else
            write(",0");
        endif;
    endif;
    num := num + 1;
end loop;
writeln();
end;