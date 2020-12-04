use strict;
use warnings;
 
print "puzzle 02b:\n";
my $numberValid = 0;
my $filename = 'input02.txt';
open(my $fh, '<:encoding(UTF-8)', $filename)
  or die "Could not open file '$filename' $!";
while (my $row = <$fh>) {
  chomp $row;
  # 5-16 b: bbbbhbbbbpbxbbbcb
  my ($firstPos, $secondPos, $letter, $password) = $row =~ /(\d+)-(\d+) (.)\: (.+)/g;
	my $matchAtFirst = (substr($password, $firstPos-1, 1) eq $letter);
	my $matchAtSecond = (substr($password, $secondPos-1, 1) eq $letter);
	if ($matchAtFirst || $matchAtSecond) {
		next if ($matchAtFirst && $matchAtSecond);
		$numberValid++;
	}
}
print "There are $numberValid valid passwords in the file\n";

