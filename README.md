This is my solution to a programming challenge, specifically [Stack Overflow Challenge #24](https://stackoverflow.com/beta/challenges/80010077/challenge-24-dont-fold).

The program takes in a JSON file that describes hands of playing cards. It then evaluates each hand according to the rules of poker, assigns it a number of points based on the type of hand, and adds those points to the overall total.

For the input file given in the challenge instructions (stored here as [src/test/resources/poker_hands.json](src/test/resources/poker_hands.json)), the program output is as follows:

```
Hand 1: 2 points
Hand 2: 1 points
Hand 3: 1 points
Hand 4: 8 points
Hand 5: 512 points
Hand 6: 8 points
Hand 7: 1 points
Hand 8: 2 points
Hand 9: 4 points
Hand 10: 2 points
Hand 11: 16 points
Hand 12: 4 points
Hand 13: 4 points
Hand 14: 2 points
Hand 15: 4 points
Hand 16: 2 points
Hand 17: 1 points
Hand 18: 8 points
Hand 19: 8 points
Hand 20: 2 points
Hand 21: 16 points
Hand 22: 1 points
Hand 23: 8 points
Hand 24: 16 points
Hand 25: 2 points
Hand 26: 1 points
Hand 27: 4 points
Hand 28: 2 points
Hand 29: 2 points
Hand 30: 1 points
Hand 31: 2 points
Hand 32: 2 points
Hand 33: 1 points
Hand 34: 256 points
Hand 35: 2 points
Hand 36: 32 points
Hand 37: 4 points
Hand 38: 8 points
Hand 39: 1 points
Hand 40: 512 points
Hand 41: 2 points
Hand 42: 256 points
Hand 43: 1 points
Hand 44: 2 points
Hand 45: 16 points
Hand 46: 2 points
Hand 47: 1 points
Hand 48: 128 points
Hand 49: 2 points
Hand 50: 8 points
Hand 51: 1 points
Hand 52: 2 points
Hand 53: 4 points
Hand 54: 8 points
Hand 55: 256 points
Hand 56: 64 points
Hand 57: 32 points
Hand 58: 4 points
Hand 59: 2 points
Hand 60: 8 points
Hand 61: 16 points
Hand 62: 1 points
Hand 63: 16 points
Hand 64: 2 points
Hand 65: 2 points
Hand 66: 8 points
Hand 67: 256 points
Hand 68: 2 points
Hand 69: 32 points
Hand 70: 32 points
Hand 71: 2 points
Hand 72: 64 points
Hand 73: 512 points
Hand 74: 2 points
Hand 75: 2 points
Hand 76: 2 points
Hand 77: 2 points
Hand 78: 2 points
Hand 79: 1 points
Hand 80: 16 points
Hand 81: 4 points
Hand 82: 8 points
TOTAL: 3248 points
```