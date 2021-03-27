#!/usr/bin/python3

import json
import sys

if __name__ == "__main__":
	cards = list()
	fields = ["convertedManaCost", "name"]

	# Parse every files given in command args
	for arg in enumerate(sys.argv[1:]):
		# We get the command value
		(_, arg) = arg
		# For each file we open it and get content as json
		with open(str(arg)) as inputstream:
			data = json.load(inputstream)
			# For every cards we create a new entry in the cards dict but we only get the fields we need
			for card in data["data"]["cards"]:
				cards.append({field:card[field] for field in fields})


	# Exporting the resutlts as card.json
	with open("cards.json", "w") as outstream:
		outstream.write(json.dumps(cards, indent = 4))
