use strict;
use warnings;
 
print "puzzle 02a: ";
my $numberValid = 0;
my $filename = 'input02.txt';
open(my $fh, '<:encoding(UTF-8)', $filename)
  or die "Could not open file '$filename' $!";
while (my $row = <$fh>) {
  chomp $row;
  # 5-16 b: bbbbhbbbbpbxbbbcb
  my ($countMin, $countMax, $letter, $password) = $row =~ /(\d+)-(\d+) (.)\: (.+)/g;
	my $count = () = $password =~ /$letter/gi;
	if (($count >= $countMin) && ($count <= $countMax)) {
		$numberValid++;
	}
}
print "There are $numberValid valid passwords in the file\n";

