use strict;
use warnings;

my @moveX = (1, 3, 5, 7, 1);
my @moveY = (1, 1, 1, 1, 2);
print "puzzle 03b:\n";
my $filename = 'input03.txt';
open(my $fh, '<:encoding(UTF-8)', $filename)
  or die "Could not open file '$filename' $!";
my @rows = ();
while (my $row = <$fh>) {
  chomp $row;
  push(@rows, $row)
}
my $bottom = $#rows;
my @hitCount = ();
my $numberOfRuns = $#moveX + 1;
for (my $index = 0; $index < $numberOfRuns; $index++) {
	my $numberTrees = 0;
	my $skiX = 0;
	my $skiY = 0;
	my $rowLength =  (length $rows[$#rows]);
	my @chars = ();
	while ($skiY < $bottom) {
		# increase Y by moveY
		$skiY += $moveY[$index];
		# increase X by moveX, looping to beginning if needed
		$skiX += $moveX[$index];
		$skiX = $skiX - $rowLength if ($skiX >= $rowLength);
		@chars = split("", $rows[$skiY]);
		my $spot = $chars[$skiX];
		$numberTrees++ if ("#" eq $spot);
	}
	print "We hit $numberTrees trees on run $index.\n";
	push (@hitCount, $numberTrees);
}
my $total = pop(@hitCount);
while (@hitCount) {
	$total *= pop(@hitCount);
}
print "Those counts multipled are: $total\n";