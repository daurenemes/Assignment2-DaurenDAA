# DAA — Assignment 2 Report — Who Goes Next

**Name:** Dauren  
**Barcode:** 253912

## 1. Correctness

Full output of `java Main 253912`:

```text
barcode,253912
room,checksum,critical_served,critical_wait
fifo,333383335000,1010,5088452
array,661882009172,1993,1229
list,661882009172,1993,1229
heap,661882009172,1993,1229

OK   FifoQueueRoom matches the fixed no-triage checksum for any barcode.
OK   SortedListRoom calls the patients in exactly the same order as the given ArrayRoom.
OK   HeapRoom calls the patients in exactly the same order as the given ArrayRoom.

OK   FifoQueueRoom throws IllegalStateException on an empty room.
OK   SortedListRoom throws IllegalStateException on an empty room.
OK   HeapRoom throws IllegalStateException on an empty room.

OK   CallLog records every call and recent(3) is non-destructive and most-recent-first.
```

All four implemented classes pass the correctness checks. `SortedListRoom` and `HeapRoom` produce exactly the same call order as the trusted `ArrayRoom`.

## 2. Complexity

### SortedListRoom.add

- **Worst case:** Θ(n)
- **Amortised:** Θ(n)
- **Why:** The list must remain sorted after every insertion, so a single `add` may have to traverse the entire list to find the correct position. There is no resizing or occasional expensive operation that could make the amortised complexity smaller.

### HeapRoom.add

- **Worst case:** Θ(n)
- **Amortised:** O(log n)
- **Why:** A normal insertion appends the patient and then performs `siftUp`, which takes O(log n). However, when the backing array is full, resizing requires copying all existing elements, which takes Θ(n). Therefore a single call can be Θ(n), while the resize cost is spread over many insertions, giving O(log n) amortised time.

## 3. Benchmark

### Table A — Whole shift

| Patients | Array | List | Heap |
|---:|---:|---:|---:|
| 1,000 | 1.87 ms | 0.97 ms | 0.65 ms |
| 5,000 | 6.90 ms | 13.03 ms | 1.58 ms |
| 20,000 | 211.37 ms | 242.51 ms | 2.43 ms |

Before running the benchmark, I expected the array and list implementations to become much slower as the number of patients increased because their expensive operations can take Θ(n). I expected the heap to scale much better because heap insertion and removal are based on the height of the heap.

From 5,000 to 20,000 patients, the measured times increased by approximately 30.6x for `array`, 18.6x for `list`, and only 1.5x for `heap`. The results therefore show the expected general trend: the heap scales much better than the array and list. The exact millisecond values are machine-dependent and can be affected by background processes and JVM/JIT behaviour.

### Table B — Add vs next

| Room | Add (1,000) | Next (1,000) |
|---|---:|---:|
| array | 0.011 ms | 33.684 ms |
| list | 66.644 ms | 0.115 ms |
| heap | 0.071 ms | 0.368 ms |

For `array`, `next` is the expensive operation because it searches through the waiting patients to find the best patient, while `add` only appends to the backing array.

For `list`, `add` is the expensive operation because the linked list must be traversed to find the correct sorted position. `next` is cheap because the next patient is always at the head of the list.

The benchmark values are specific to my machine and run conditions, so they should not be treated as exact constants.

## 4. Triage effect

For the most severe patients (severity 5), **983 patients remained uncalled without triage, while 0 remained uncalled with triage**. Therefore, triage ensured that all severity-5 patients in this dataset were called before the end of the shift.

For the least severe patients (severity 1), **2,573 remained uncalled without triage, compared with 5,039 with triage**. Thus, triage significantly increases the number of severity-1 patients left waiting because higher-severity patients are prioritised.
