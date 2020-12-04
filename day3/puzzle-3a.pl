use strict;
use warnings;

print "puzzle 03a:";
my $moveX = 3;
my $moveY = 1;
my $numberTrees = 0;
my $skiX = 0;
my $skiY = 0;
my $filename = 'input03.txt';
open(my $fh, '<:encoding(UTF-8)', $filename)
  or die "Could not open file '$filename' $!";
my @rows = ();
# fill an array with the file lines
while (my $row = <$fh>) {
  chomp $row;
  push(@rows, $row)
}
my $bottom = $#rows;
my $rowLength =  (length $rows[$#rows]);
my @chars = ();
while ($skiY < $bottom) {
	# increase Y by moveY
	$skiY += $moveY;
	# increase X by moveX, looping to beginning if needed
	$skiX += $moveX;
	$skiX = $skiX - $rowLength if ($skiX >= $rowLength);
	@chars = split("", $rows[$skiY]);
	my $spot = $chars[$skiX];
	$numberTrees++ if ("#" eq $spot);
}
print " We hit $numberTrees trees on the way down.\n";
