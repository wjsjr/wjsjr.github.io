package scheduler

import (
	"sync"
)

/*
DEQueue is a double-ended queue built for a work stealing task scheduler. Coarse grained locking is used for synchronization.
*/

/*
DEQueue Interface exposes 3 methods that scheduler uses to interact with it
*/
type DEqueue interface {
	PushBottom(t *Task)
	PopBottom() *Task
	PopTop() *Task
}

/*
DEQueue Struct
*/

type dequeue struct {
	l    *sync.Mutex
	head *qNode
	tail *qNode
}

/*
QNode Struct
*/

type qNode struct {
	next *qNode
	last *qNode
	body *Task
}

/*
Wrap Task in QNode
*/

func newQNode(t *Task) *qNode {
	return &qNode{nil, nil, t}
}

/*
Construct new DEqueue
*/

func NewDEqueue() DEqueue {
	h := newQNode(nil)
	t := newQNode(nil)
	h.next = t
	t.last = h
	return &dequeue{new(sync.Mutex), h, t}
}

/*
Push task to end of Q
*/
func (d *dequeue) PushBottom(t *Task) {
	d.l.Lock()
	defer d.l.Unlock()
	newTail := newQNode(nil)
	oldTail := d.tail
	oldTail.body = t
	oldTail.next = newTail
	newTail.last = oldTail
	d.tail = newTail

}

/*
Pop off end of Q
*/
func (d *dequeue) PopBottom() *Task {
	d.l.Lock()
	defer d.l.Unlock()

	//Handle Empty Q
	if d.isEmpty() {
		return nil
	}

	oldBottom := d.tail.last
	newBottom := oldBottom.last

	newBottom.next = d.tail
	d.tail.last = newBottom
	return oldBottom.body
}

/*
Pop off front of Q
*/
func (d *dequeue) PopTop() *Task {

	d.l.Lock()
	defer d.l.Unlock()

	//Handle Empty Q
	if d.isEmpty() {
		return nil
	}

	oldTop := d.head.next
	newTop := oldTop.next

	newTop.last = d.head
	d.head.next = newTop
	return oldTop.body

}

/*
Helper function to determine if Q is Empty
*/
func (d *dequeue) isEmpty() bool {
	return d.head.next.body == nil
}
