#!/bin/bash

# variables for the different directories
# chocobo_dir has all the types of chocobos
chocobo_dir=Chocobos
# textures holds any overlays for the Chocobo
texture_dir=Textures
# variation holds any two types of certain color Chocobo (male/female)
variation_dir=Variations
# the output directory will be where the generated textures go
output_dir=Output

# remove the output directory
rm -rf $output_dir

# we will now generate textures
echo "Generating textures..."

# loop over files with png extension
for f in `cd $chocobo_dir && ls && cd ..` 
do
	# store color as the filename without extension
	color=$(basename $f)
	color=${color%.*}

	# generating all textures for this chocobo color
	echo "> Generating textures for $f..."
	# loop over the textures to overlay
	for t in `cd $texture_dir && ls && cd ..` 
	do
		# generating this texture
		echo ">> Generating $t..."

		# store the texture as the filename without extension
		texture=$(basename "$t")
		texture=${texture%.*}

		# make the directory 
		mkdir -p $output_dir/$texture

		# loop over each variation
		for v in `cd $variation_dir && ls && cd ..`
		do 
			# generating the texture
			echo ">>> Generating for $v"

			# store the variation as the filename without extension
			variation=$(basename "$v")
			variation=${variation%.*}

			# make the variation directory
			mkdir -p $output_dir/$texture/$variation

			# create the new filename
			filename=$output_dir/$texture/$variation/$color.png
			# first pass over with variation
			composite -gravity center $variation_dir/$v $chocobo_dir/$f $filename
			# then pass over with the texture overlay
			composite -gravity center $texture_dir/$t $filename $filename
		done # done with variation loop
	done # done with texture loop
done # done with chocobo loop

