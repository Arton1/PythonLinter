from abc import ABC
import math

def print(x):
	pass

y = 3

class Klasa:

	def __init__(self):
		xIsFive = 5

	def funkcja(self, x: int) -> bool:
		if x == 1:
			print(x)
		elif x == 2:
			x += 2
		else:
			return x == 1
		return True

	def wywolanie0123() -> float:
		return 0.1

z = Klasa.wywolanie0123()