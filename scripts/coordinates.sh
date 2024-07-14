[ ! -e route_polygone.json ] || rm route_polygone.json
cp $(pwd)/test_coords.txt test
cat test | while read -r line
do
  echo "LatLngNew(${line::-2})," >> route_polygone.json
done
lastCoords=$(tail -n 1 test | tr -d ' ')
echo "LatLngNew(${lastCoords::-2})" >> route_polygone.json


#sed 's/.$//' test_coords.txt > route_polygone.json
#sed 's/_//' route_polygone.json > route_polygone1.json

#arr=( )
#while read -r name _; do
#  [[ $name = *.tst ]] || continue # skip lines not containing .tst
#  arr+=( "${name%.tst}" )
#done <input.txt
#
#declare -p arr


#cp $(pwd)/test_coords.txt test
#while read -r line; do
#  echo "$line" | tr ',0' ','
#done <test