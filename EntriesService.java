package ctg.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ctg.dao.EntriesRepository;
import ctg.model.Entries;

@Service
@Transactional
public class EntriesService {
	private final EntriesRepository entriesRepository;

	public EntriesService(EntriesRepository entriesRepository) {
		this.entriesRepository = entriesRepository;
	}

	public List<Entries> findAll() {
		List<Entries> entries = new ArrayList<>();
		for (Entries entry : entriesRepository.findAll()) {
			entries.add(entry);
		}
		return entries;
	}

	public Entries findById(int id) {
		return entriesRepository.findOne(id);
	}

	public void save(Entries entries) {
		entriesRepository.save(entries);
	}
}
