defmodule Stack do

  def push([],element) do
    [element]
  end

  def push(stack, element) do
    [element | stack]
  end

  def pop([]) do
    []
  end

  def pop([h | t]) do
    t
  end
end
